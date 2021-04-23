package by.bstu.vs.stpms.courier_application.model.util.application

import android.app.Application
import by.bstu.vs.stpms.courier_application.model.network.NetworkService

class CourierApplication: Application() {
    init {
        NetworkService.context = this
    }
}