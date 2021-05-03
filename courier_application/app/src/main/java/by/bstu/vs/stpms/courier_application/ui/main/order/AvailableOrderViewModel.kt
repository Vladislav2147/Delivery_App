package by.bstu.vs.stpms.courier_application.ui.main.order

import android.util.EventLog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bstu.vs.stpms.courier_application.model.database.entity.Order
import by.bstu.vs.stpms.courier_application.model.service.OrderService
import by.bstu.vs.stpms.courier_application.model.util.event.Event

class AvailableOrderViewModel : ViewModel() {

    val ordersLiveData = MutableLiveData<Event<List<Order>>>()
    private val service = OrderService

    fun getAvailableOrders() {
        service.getAvailableOrders(ordersLiveData)
    }
}