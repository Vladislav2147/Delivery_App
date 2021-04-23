package by.bstu.vs.stpms.courier_application.model.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import by.bstu.vs.stpms.courier_application.model.database.CourierDatabase
import by.bstu.vs.stpms.courier_application.model.database.contract.RoleType
import by.bstu.vs.stpms.courier_application.model.database.entity.Role
import by.bstu.vs.stpms.courier_application.model.database.entity.User
import by.bstu.vs.stpms.courier_application.model.database.entity.UserRole
import by.bstu.vs.stpms.courier_application.model.exception.CourierNetworkException
import by.bstu.vs.stpms.courier_application.model.network.NetworkService
import by.bstu.vs.stpms.courier_application.model.network.NetworkService.loginService
import by.bstu.vs.stpms.courier_application.model.network.dto.UserDto
import by.bstu.vs.stpms.courier_application.model.network.util.event.Event
import by.bstu.vs.stpms.courier_application.model.repository.mapper.UserMapper
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
    private val db: CourierDatabase

    init {
        db = CourierDatabase.getDatabase(context)
    }

    fun login(login: String, password: String, userLiveData: MutableLiveData<Event<User>>) {
        userLiveData.postValue(Event.loading())
        Log.d("TAGGG", "mew")
        service.login(login, password, true).enqueue(CustomUserCallback(userLiveData) { user ->
            //TODO db flex
            db.userDao.insert(user)
            for (role in user.roles) {
                db.userRoleDao.insert(UserRole().apply {
                    userId = user.id
                    roleId = role.id
                })
            }
            db.changeDao.setUpToDate("user", user.id)
            userLiveData.postValue(Event.success(user))
        })
    }

    fun tryAutoLogin(userLiveData: MutableLiveData<Event<User>>) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1500)
            service.currentUser().enqueue(CustomUserCallback(userLiveData) { user ->
                userLiveData.postValue(Event.success(user))
            })
        }
    }

    fun logout() {
        db.clearAllTables()
        service.logout()
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