package by.bstu.vs.stpms.courier_application.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bstu.vs.stpms.courier_application.model.database.entity.Change
import by.bstu.vs.stpms.courier_application.model.exception.CourierNetworkException
import by.bstu.vs.stpms.courier_application.model.network.NetworkService
import by.bstu.vs.stpms.courier_application.model.network.NetworkService.context
import by.bstu.vs.stpms.courier_application.model.network.dto.UserDto
import by.bstu.vs.stpms.courier_application.model.network.util.event.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AuthViewModel: ViewModel() {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val change = Change("som", 1, "op", Calendar.getInstance())

//            CourierDatabase.getDatabase(context).changeDao.insert(change)
//            CourierDatabase.getDatabase(context).productDao.selectAll()
        }
    }

    val userLiveData = MutableLiveData<Event<UserDto>>()
    var loginLiveData = MutableLiveData<String>()
    var passwordLiveData = MutableLiveData<String>()

    private val userCallback = object :
            Callback<UserDto> {
        override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
            try {
                when (response.code()) {
                    200 -> {
                        val dto: UserDto = response.body()!!
                        userLiveData.postValue(Event.success(dto))
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

    fun tryAutoLogin() {
        viewModelScope.launch {
            delay(1500)
            NetworkService.loginService().currentUser().enqueue(userCallback)
        }
    }
    fun login() {
        userLiveData.postValue(Event.loading())
        NetworkService.loginService().login(loginLiveData.value, passwordLiveData.value, true).enqueue(userCallback)
    }
}