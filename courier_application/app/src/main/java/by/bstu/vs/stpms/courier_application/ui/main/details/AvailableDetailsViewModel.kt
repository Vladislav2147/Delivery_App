package by.bstu.vs.stpms.courier_application.ui.main.details

import android.util.EventLog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.bstu.vs.stpms.courier_application.model.database.entity.Order
import by.bstu.vs.stpms.courier_application.model.exception.CourierNetworkException
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository
import by.bstu.vs.stpms.courier_application.model.service.OrderService
import by.bstu.vs.stpms.courier_application.model.util.event.Event
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AvailableDetailsViewModel : ViewModel() {
    val orderLiveData = MutableLiveData<Order>()
    val responseLiveData = MutableLiveData<Event<ResponseBody>>()

    val service = OrderService

    fun acceptOrder() {
        service.accept(orderLiveData, responseLiveData)
    }
}