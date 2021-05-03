package by.bstu.vs.stpms.courier_application.model.util.application

import android.app.Application
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository
import by.bstu.vs.stpms.courier_application.model.util.ConnectionListener

class CourierApplication: Application() {
    init {
        NetworkRepository.context = this
    }
}