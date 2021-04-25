package by.bstu.vs.stpms.courier_application.model.service

import androidx.lifecycle.MutableLiveData
import by.bstu.vs.stpms.courier_application.model.database.CourierDatabase
import by.bstu.vs.stpms.courier_application.model.database.entity.Order
import by.bstu.vs.stpms.courier_application.model.exception.CourierNetworkException
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository.context
import by.bstu.vs.stpms.courier_application.model.network.dto.OrderDto
import by.bstu.vs.stpms.courier_application.model.service.mapper.OrderMapper
import by.bstu.vs.stpms.courier_application.model.util.event.Event
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.mapstruct.factory.Mappers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderService {

    private val orderApi = NetworkRepository.orderApi()
    private val db: CourierDatabase = CourierDatabase.getDatabase(context)

    fun getAvailableOrders(ordersLiveData: MutableLiveData<Event<List<Order>>>) {
        ordersLiveData.postValue(Event.loading())
        NetworkRepository.orderApi().availableOrders.enqueue(object: Callback<List<OrderDto>> {
            override fun onResponse(call: Call<List<OrderDto>>, response: Response<List<OrderDto>>) {
                try {
                    when (response.code()) {
                        //Успешное получение данных
                        200 -> {
                            val mapper = Mappers.getMapper(OrderMapper::class.java)
                            val orders = mapper.dtosToEntities(response.body())
                            ordersLiveData.postValue(Event.success(orders))
                        }
                        //Возвращается сервером, если пользователь не имеет роли курьера
                        403 -> {
                            throw CourierNetworkException("Your account is not verified")
                        }
                        else -> {
                            throw CourierNetworkException("Network Troubles")
                        }
                    }
                } catch (e: CourierNetworkException) {
                    ordersLiveData.postValue(Event.error(e))
                }
            }

            override fun onFailure(call: Call<List<OrderDto>>, t: Throwable) {
                ordersLiveData.postValue(Event.error(t))
            }
        })
    }

}