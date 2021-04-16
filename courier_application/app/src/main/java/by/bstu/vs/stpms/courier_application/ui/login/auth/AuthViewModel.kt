package by.bstu.vs.stpms.courier_application.ui.login.auth

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bstu.vs.stpms.courier_application.model.entity.User
import by.bstu.vs.stpms.courier_application.model.exception.CourierNetworkException
import by.bstu.vs.stpms.courier_application.model.retrofit.NetworkService
import by.bstu.vs.stpms.courier_application.model.retrofit.event.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {

    val userLiveData = MutableLiveData<Event<User>>()
    var loginLiveData = MutableLiveData<String>()
    var passwordLiveData = MutableLiveData<String>()

    fun login() {
        userLiveData.postValue(Event.loading())
        NetworkService.loginService().login(loginLiveData.value, passwordLiveData.value).enqueue(object :
            Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                try {
                    when(response.code()) {
                        200 -> {
                            val dto: User = response.body()!!
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

            override fun onFailure(call: Call<User>, t: Throwable) {
                userLiveData.postValue(Event.error(t))
            }

        })
    }
}