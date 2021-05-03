package by.bstu.vs.stpms.courier_application.ui.main.profile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.model.database.contract.RoleType
import by.bstu.vs.stpms.courier_application.model.database.entity.Role
import by.bstu.vs.stpms.courier_application.model.database.entity.User
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository.context
import by.bstu.vs.stpms.courier_application.model.service.UserService
import by.bstu.vs.stpms.courier_application.model.util.event.Event
import kotlinx.coroutines.launch
import java.util.stream.Collectors

class ProfileViewModel : ViewModel() {

    val userLiveData = MutableLiveData<Event<User>>()
    private val userService = UserService

    fun logout() {
        viewModelScope.launch {
            userService.logout()
            context
                    .getSharedPreferences(
                            context.getString(R.string.shared_prefs_cookies),
                            Context.MODE_PRIVATE
                    )
                    .edit()
                    .clear()
                    .apply()
        }
    }

    fun getUser() {
        userService.getCurrentUser(userLiveData)
    }

    fun formatRoles(roles: Set<Role>?): String {

        val formatted: String

        if (roles == null) {
            formatted = ""
        } else {
            val roleTypeSet = roles.stream().map { role -> RoleType.valueOf(role.name) }.collect(Collectors.toSet())

            formatted = when {
                roleTypeSet.contains(RoleType.ROLE_ADMIN) -> {
                    RoleType.ROLE_ADMIN.formattedName
                }
                roleTypeSet.contains(RoleType.ROLE_COURIER) -> {
                    RoleType.ROLE_COURIER.formattedName
                }
                else -> {
                    RoleType.ROLE_BASIC.formattedName
                }
            }
        }
        return formatted;
    }
}