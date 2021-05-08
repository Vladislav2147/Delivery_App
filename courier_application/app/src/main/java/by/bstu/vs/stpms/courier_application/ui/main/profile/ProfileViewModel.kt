package by.bstu.vs.stpms.courier_application.ui.main.profile

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.model.database.contract.RoleType
import by.bstu.vs.stpms.courier_application.model.database.entity.Role
import by.bstu.vs.stpms.courier_application.model.database.entity.Stats
import by.bstu.vs.stpms.courier_application.model.database.entity.User
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository.context
import by.bstu.vs.stpms.courier_application.model.service.UserService
import by.bstu.vs.stpms.courier_application.model.util.event.Event
import kotlinx.coroutines.launch
import java.util.stream.Collectors

class ProfileViewModel : ViewModel() {

    val userLiveData = MutableLiveData<Event<User>>()
    val statsLiveData = MutableLiveData<Event<Stats>>()
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

    fun getStats() {
        userService.getUserStats(statsLiveData)
    }

    fun formatRoles(roles: Set<Role>?): String {

        val formatted: String

        formatted = if (roles == null) {
            ""
        } else {
            val roleTypeSet = roles.stream().map { role -> RoleType.valueOf(role.name) }.collect(Collectors.toSet())

            if (roleTypeSet.size == 1 && roleTypeSet.contains(RoleType.ROLE_BASIC)) "Unverified"
            else "Verified"

        }
        return formatted
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setDrawable(role: String): Drawable? {
        return if (role == "Verified") context.getDrawable(R.drawable.ic_baseline_verified_16)
        else context.getDrawable(R.drawable.ic_baseline_not_verified_16)
    }
}