package by.bstu.vs.stpms.courier_application.ui.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import by.bstu.vs.stpms.courier_application.model.retrofit.NetworkService

open class NetworkViewModel(application: Application): AndroidViewModel(application) {
    val service = NetworkService(application)
}