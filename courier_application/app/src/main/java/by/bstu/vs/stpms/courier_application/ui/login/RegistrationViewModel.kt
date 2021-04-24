package by.bstu.vs.stpms.courier_application.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bstu.vs.stpms.courier_application.model.database.entity.User
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository
import by.bstu.vs.stpms.courier_application.model.service.UserService
import by.bstu.vs.stpms.courier_application.model.util.event.Event

class RegistrationViewModel : ViewModel() {

    val userLiveData = MutableLiveData<Event<User>>()
    var firstNameLiveData = MutableLiveData("")
    var passwordLiveData = MutableLiveData("")

    private val userService = UserService(NetworkRepository.context)
}