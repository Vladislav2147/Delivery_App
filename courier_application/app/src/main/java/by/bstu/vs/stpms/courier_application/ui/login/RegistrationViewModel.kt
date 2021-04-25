package by.bstu.vs.stpms.courier_application.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bstu.vs.stpms.courier_application.model.database.entity.User
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository
import by.bstu.vs.stpms.courier_application.model.network.dto.UserDto
import by.bstu.vs.stpms.courier_application.model.service.UserService
import by.bstu.vs.stpms.courier_application.model.util.event.Event

class RegistrationViewModel : ViewModel() {

    val responseLiveData = MutableLiveData<Event<Void>>()

    var firstNameLiveData = MutableLiveData("")
    var secondNameLiveData = MutableLiveData("")
    var emailLiveData = MutableLiveData("")
    var phoneLiveData = MutableLiveData("")
    var passwordLiveData = MutableLiveData("")
    var confirmPasswordLiveData = MutableLiveData("")

    private val userService = UserService(NetworkRepository.context)

    fun signUp() {
        val userDto = UserDto().apply {
            firstName = firstNameLiveData.value
            secondName = secondNameLiveData.value
            email = emailLiveData.value
            phone = phoneLiveData.value
            password = passwordLiveData.value
            confirmPassword = confirmPasswordLiveData.value
        }

        userService.signUp(userDto, responseLiveData)
    }
}