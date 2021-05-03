package by.bstu.vs.stpms.courier_application.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bstu.vs.stpms.courier_application.model.database.entity.User
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository.context
import by.bstu.vs.stpms.courier_application.model.util.event.Event
import by.bstu.vs.stpms.courier_application.model.service.UserService

class AuthViewModel: ViewModel() {

    val userLiveData = MutableLiveData<Event<User>>()
    var loginLiveData = MutableLiveData("")
    var passwordLiveData = MutableLiveData("")

    private val userService = UserService

    fun tryAutoLogin() {
        userService.tryAutoLogin(userLiveData)
    }
    fun login() {
        userService.login(loginLiveData.value!!, passwordLiveData.value!!, userLiveData)
    }
}