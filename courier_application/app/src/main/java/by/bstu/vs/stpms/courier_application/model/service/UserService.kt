package by.bstu.vs.stpms.courier_application.model.service

import android.content.Context
import androidx.lifecycle.MutableLiveData
import by.bstu.vs.stpms.courier_application.model.database.CourierDatabase
import by.bstu.vs.stpms.courier_application.model.database.entity.Stats
import by.bstu.vs.stpms.courier_application.model.database.entity.User
import by.bstu.vs.stpms.courier_application.model.database.entity.UserRole
import by.bstu.vs.stpms.courier_application.model.exception.CourierNetworkException
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository.context
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository.isOnline
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository.userApi
import by.bstu.vs.stpms.courier_application.model.network.dto.StatsDto
import by.bstu.vs.stpms.courier_application.model.network.dto.UserDto
import by.bstu.vs.stpms.courier_application.model.service.mapper.OrderMapper
import by.bstu.vs.stpms.courier_application.model.service.mapper.StatsMapper
import by.bstu.vs.stpms.courier_application.model.service.mapper.UserMapper
import by.bstu.vs.stpms.courier_application.model.util.event.Event
import by.bstu.vs.stpms.courier_application.model.util.livedata.observeOnce
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.mapstruct.factory.Mappers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object UserService {

    private val userApi = userApi()
    private val db: CourierDatabase = CourierDatabase.getDatabase(context)

    //Вызывается при нажатии на кнопку входа в форме логина
    fun login(login: String, password: String, userLiveData: MutableLiveData<Event<User>>) {
        userLiveData.postValue(Event.loading())

        //Формируем запрос к серверу, ответ - объект UserDto, при помощи CustomUserCallback
        //осуществляем обработку ошибок и отображение в User
        userApi.login(login, password, true).enqueue(CustomUserCallback(userLiveData) { user ->
            insertUserToDb(user)
            userLiveData.postValue(Event.success(user))
        })
    }

    //Вызывается при регистрации
    fun signUp(userDto: UserDto, responseLiveData: MutableLiveData<Event<Void>>) {
        responseLiveData.postValue(Event.loading())
        userApi.signUp(userDto).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    when (response.code()) {
                        //Успешная регистрация
                        201 -> {
                            responseLiveData.postValue(Event.success(null))
                        }
                        //Возвращается сервером при ошибке валидации
                        400 -> {
                            throw CourierNetworkException("Invalid form")
                        }
                        //Возвращается сервером, если пользователь уже существует
                        403 -> {
                            val gson = Gson()
                            val jsonObject: JsonObject = gson.fromJson(response.errorBody()?.string(), JsonObject::class.java)
                            throw CourierNetworkException(jsonObject["message"].asString)
                        }
                        else -> {
                            throw CourierNetworkException("Network Troubles")
                        }
                    }
                } catch (e: CourierNetworkException) {
                    responseLiveData.postValue(Event.error(e))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                responseLiveData.postValue(Event.error(t))
            }

        })
    }

    //Вызывается при запуске приложения, реализует логику попытки автоматического входа
    fun tryAutoLogin(userLiveData: MutableLiveData<Event<User>>) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            if(isOnline(context)) {
                //Если есть подключение к сети, попытка получить с сервера текущего пользователя
                userApi.currentUser().enqueue(CustomUserCallback(userLiveData) { user ->
                    CoroutineScope(Dispatchers.IO).launch {
                        val dbUser = db.userDao.getUser()?.firstOrNull()
                        if (dbUser == null) {
                            insertUserToDb(user)
                        }
                        userLiveData.postValue(Event.success(user))
                    }
                })
            } else {
                //Если подключения к сети нет, попытка получить текущего пользователя с локальной бд
                val user = db.userDao.getUser()?.firstOrNull()
                if (user != null) {
                    user.roles = HashSet(db.userRoleDao.getUserRolesByUserId(user.id))
                    userLiveData.postValue(Event.success(user))
                } else {
                    userLiveData.postValue(Event.error(CourierNetworkException("User not found")))
                }
            }
        }
    }

    suspend fun logout() {
        //При выходе из учетной записи очищаем локальную базу данных (с информацией текущего пользователя)
        db.clear()
        //Запрос серверу для выхода (удаляет текущую сессию и cookies)
        userApi.logout()
    }

    fun getCurrentUser(userLiveData: MutableLiveData<Event<User>>) {
        if(isOnline(context)) {
            //Если есть подключение к сети, попытка получить с сервера текущего пользователя
            userApi.currentUser().enqueue(CustomUserCallback(userLiveData) { user ->
                userLiveData.postValue(Event.success(user))
            })
        } else {
            //Если подключения к сети нет, попытка получить текущего пользователя с локальной бд
            CoroutineScope(Dispatchers.IO).launch {
                val user = db.userDao.getUser()?.firstOrNull()
                if (user != null) {
                    user.roles = HashSet(db.userRoleDao.getUserRolesByUserId(user.id))
                    userLiveData.postValue(Event.success(user))
                } else {
                    userLiveData.postValue(Event.error(CourierNetworkException("User not found")))
                }

            }
        }
    }

    //Метод используется для получения статистики курьера
    fun getUserStats(statsLiveData: MutableLiveData<Event<Stats>>) {
        statsLiveData.postValue(Event.loading())
        userApi.stats.enqueue(object : Callback<StatsDto> {
            override fun onResponse(call: Call<StatsDto>, response: Response<StatsDto>) {
                try {
                    when (response.code()) {
                        //Успешное получение данных
                        200 -> {
                            val mapper = Mappers.getMapper(StatsMapper::class.java)
                            val stats = mapper.dtoToEntity(response.body())
                            statsLiveData.postValue(Event.success(stats))
                        }
                        else -> {
                            throw CourierNetworkException("Network Troubles")
                        }
                    }
                } catch (e: CourierNetworkException) {
                    statsLiveData.postValue(Event.error(e))
                }
            }

            override fun onFailure(call: Call<StatsDto>, t: Throwable) {
                statsLiveData.postValue(Event.error(t))
            }
        })
    }

    private fun insertUserToDb(user: User) {
        //Вставляем объект User в базу данных
        db.userDao.insert(user)
        //Вставляем роли пользователя в базу данных
        for (role in user.roles) {
            db.userRoleDao.insert(UserRole().apply {
                userId = user.id
                roleId = role.id
            })
        }
        //В таблице изменений помечаем изменение как актуальное
        db.changeDao.setUpToDate("user", user.id)
    }

    private class CustomUserCallback(val userLiveData: MutableLiveData<Event<User>>, val onStatusOkCallback: (User) -> Unit) : Callback<UserDto> {
        override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
            try {
                when (response.code()) {
                    200 -> {
                        val dto: UserDto = response.body()!!
                        val mapper = Mappers.getMapper(UserMapper::class.java)
                        val user = mapper.dtoToEntity(dto)
                        onStatusOkCallback.invoke(user)
                    }
                    401 -> {
                        throw CourierNetworkException("Invalid Credentials")
                    }
                    else -> {
                        throw CourierNetworkException("Network Troubles")
                    }
                }
            } catch (e: CourierNetworkException) {
                userLiveData.postValue(Event.error(e))
            }
        }

        override fun onFailure(call: Call<UserDto>, t: Throwable) {
            userLiveData.postValue(Event.error(t))
        }
    }

}