package by.bstu.vs.stpms.courier_application.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bstu.vs.stpms.courier_application.model.database.entity.User
import by.bstu.vs.stpms.courier_application.model.network.NetworkService.context
import by.bstu.vs.stpms.courier_application.model.util.event.Event
import by.bstu.vs.stpms.courier_application.model.repository.UserRepository

class AuthViewModel: ViewModel() {

    val userLiveData = MutableLiveData<Event<User>>()
    var loginLiveData = MutableLiveData<String>()
    var passwordLiveData = MutableLiveData<String>()

    val repository = UserRepository(context)

//    private val userCallback = object :
//            Callback<UserDto> {
//        override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
//            try {
//                when (response.code()) {
//                    200 -> {
//                        val dto: UserDto = response.body()!!
//                        userLiveData.postValue(Event.success(dto))
//                    }
//                    401 -> {
//                        throw CourierNetworkException("Invalid Credentials")
//                    }
//                    else -> {
//                        throw CourierNetworkException("Network Troubles")
//                    }
//                }
//            } catch (e: CourierNetworkException) {
//                userLiveData.postValue(Event.error(e))
//            }
//        }
//
//        override fun onFailure(call: Call<UserDto>, t: Throwable) {
//            userLiveData.postValue(Event.error(t))
//        }
//    }

    fun tryAutoLogin() {
        repository.tryAutoLogin(userLiveData)
    }
    fun login() {
        repository.login(loginLiveData.value!!, passwordLiveData.value!!, userLiveData)
    }
}