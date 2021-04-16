package by.bstu.vs.stpms.courier_application.ui.login.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bstu.vs.stpms.courier_application.model.retrofit.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is login Fragment"
    }
    val text: LiveData<String> = _text

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        NetworkService.loginService().login(email, password).enqueue(object :
            Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                response.body()?.let {
                    if (it.contains("ROLE_ADMIN")) {

                    }
                }

            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                print(t.message)
            }


        })
    }
}