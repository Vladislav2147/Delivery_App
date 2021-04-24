package by.bstu.vs.stpms.courier_application.model.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import by.bstu.vs.stpms.courier_application.model.database.CourierDatabase
import by.bstu.vs.stpms.courier_application.model.database.entity.User
import by.bstu.vs.stpms.courier_application.model.database.entity.UserRole
import by.bstu.vs.stpms.courier_application.model.exception.CourierNetworkException
import by.bstu.vs.stpms.courier_application.model.network.NetworkService.context
import by.bstu.vs.stpms.courier_application.model.network.NetworkService.isOnline
import by.bstu.vs.stpms.courier_application.model.network.NetworkService.loginService
import by.bstu.vs.stpms.courier_application.model.network.dto.UserDto
import by.bstu.vs.stpms.courier_application.model.repository.mapper.UserMapper
import by.bstu.vs.stpms.courier_application.model.util.event.Event
import by.bstu.vs.stpms.courier_application.model.util.livedata.observeOnce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.mapstruct.factory.Mappers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(context: Context?) {

    private val service = loginService()
    private val db: CourierDatabase = CourierDatabase.getDatabase(context)

    //Вызывается при нажатии на кнопку входа в форме логина
    fun login(login: String, password: String, userLiveData: MutableLiveData<Event<User>>) {
        userLiveData.postValue(Event.loading())

        //Формируем запрос к серверу, ответ - объект UserDto, при помощи CustomUserCallback
        //осуществляем обработку ошибок и отображение в User
        service.login(login, password, true).enqueue(CustomUserCallback(userLiveData) { user ->
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
            userLiveData.postValue(Event.success(user))
        })
    }

    //Вызывается при запуске приложения, реализует логику попытки автоматического входа
    fun tryAutoLogin(userLiveData: MutableLiveData<Event<User>>) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            if(isOnline(context)) {
                //Если есть подключение к сети, попытка получить с сервера текущего пользователя
                service.currentUser().enqueue(CustomUserCallback(userLiveData) { user ->
                    userLiveData.postValue(Event.success(user))
                })
            } else {
                //Если подключения к сети нет, попытка получить текущего пользователя с локальной бд
                CoroutineScope(Dispatchers.Main).launch {
                    db.userDao.user.observeOnce { users ->
                        val user = users.firstOrNull()
                        if (user != null) {
                            db.userRoleDao.getUserRolesByUserId(user.id).observeOnce {
                                user.roles = HashSet(it)
                                userLiveData.postValue(Event.success(user))
                            }
                        } else {
                            userLiveData.postValue(Event.error(CourierNetworkException("User not found")))
                        }
                    }
                }
            }
        }
    }

    fun logout() {
        //При выходе из учетной записи очищаем локальную базу данных (с информацией текущего пользователя)
        db.clearAllTables()
        //Запрос серверу для выхода (удаляет текущую сессию и cookies)
        service.logout()
    }

    fun getCurrentUser(userLiveData: MutableLiveData<Event<User>>) {
        if(isOnline(context)) {
            //Если есть подключение к сети, попытка получить с сервера текущего пользователя
            service.currentUser().enqueue(CustomUserCallback(userLiveData) { user ->
                userLiveData.postValue(Event.success(user))
            })
        } else {
            //Если подключения к сети нет, попытка получить текущего пользователя с локальной бд
            db.userDao.user.observeOnce {
                val optionalUser = it.stream().findFirst()
                if (optionalUser.isPresent) {
                    userLiveData.postValue(Event.success(optionalUser.get()))
                } else {
                    userLiveData.postValue(Event.error(CourierNetworkException("User not found")))
                }
            }
        }
    }

    private class CustomUserCallback(val userLiveData: MutableLiveData<Event<User>>, val onStatusOkCallback: (User) -> Unit) : Callback<UserDto> {
        override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
            try {
                when (response.code()) {
                    200 -> {
                        val dto: UserDto = response.body()!!
                        val mapper = Mappers.getMapper(UserMapper::class.java)
                        var user = mapper.dtoToEntity(dto)
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