package by.bstu.vs.stpms.courier_application.ui.main.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bstu.vs.stpms.courier_application.model.database.entity.Order
import by.bstu.vs.stpms.courier_application.model.service.OrderService
import by.bstu.vs.stpms.courier_application.model.util.event.Event
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class ActiveDetailsViewModel : ViewModel() {

    val orderLiveData = MutableLiveData<Order>()
    val responseLiveData = MutableLiveData<Event<ResponseBody>>()

    val service = OrderService

    fun declineOrder() {
        viewModelScope.launch {
            service.decline(orderLiveData, responseLiveData)
        }
    }
}