package by.bstu.vs.stpms.courier_application.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bstu.vs.stpms.courier_application.model.database.entity.User
import by.bstu.vs.stpms.courier_application.model.network.NetworkService.context
import by.bstu.vs.stpms.courier_application.model.util.event.Event
import by.bstu.vs.stpms.courier_application.model.repository.UserRepository

class AuthViewModel: ViewModel() {

    val userLiveData = MutableLiveData<Event<User>>()
    var loginLiveData = MutableLiveData("")
    var passwordLiveData = MutableLiveData("")

    val repository = UserRepository(context)

    fun tryAutoLogin() {
        repository.tryAutoLogin(userLiveData)
    }
    fun login() {
        repository.login(loginLiveData.value!!, passwordLiveData.value!!, userLiveData)
    }
}