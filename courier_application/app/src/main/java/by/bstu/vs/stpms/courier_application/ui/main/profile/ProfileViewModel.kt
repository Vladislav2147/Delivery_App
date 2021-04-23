package by.bstu.vs.stpms.courier_application.ui.main.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.model.network.NetworkService
import by.bstu.vs.stpms.courier_application.model.repository.UserRepository

class ProfileViewModel : ViewModel() {

    val repository = UserRepository(NetworkService.context)

    fun logout() {
        repository.logout()
        NetworkService.context
            .getSharedPreferences(
                NetworkService.context.getString(R.string.shared_prefs_cookies),
                Context.MODE_PRIVATE
            )
            .edit()
            .clear()
            .apply()

    }

}