package by.bstu.vs.stpms.courier_application.ui.main.details

import android.util.EventLog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bstu.vs.stpms.courier_application.model.database.entity.Order
import by.bstu.vs.stpms.courier_application.model.util.event.Event

class AvailableOrderDetailsViewModel : ViewModel() {
    val orderLiveData = MutableLiveData<Order>()
    val responseLiveData = MutableLiveData<Event<Order>>()



}