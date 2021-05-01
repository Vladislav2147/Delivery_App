package by.bstu.vs.stpms.courier_application.model.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import by.bstu.vs.stpms.courier_application.model.database.CourierDatabase
import by.bstu.vs.stpms.courier_application.model.database.entity.Order
import by.bstu.vs.stpms.courier_application.model.exception.CourierNetworkException
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository.context
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository.isOnline
import by.bstu.vs.stpms.courier_application.model.network.dto.OrderDto
import by.bstu.vs.stpms.courier_application.model.service.mapper.OrderMapper
import by.bstu.vs.stpms.courier_application.model.util.event.Event
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
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

    fun getActiveOrders() {
        //TODO online & offline
        //TODO if isuptodate check
        if (isOnline(context)) {

        } else {

        }

    }

    fun accept(orderLiveData: MutableLiveData<Order>, responseLiveData: MutableLiveData<Event<ResponseBody>>) {
        responseLiveData.postValue(Event.loading())
        orderLiveData.value?.let {  order ->
            NetworkRepository.orderApi().acceptOrder(order.id).enqueue(object: Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    when (response.code()) {
                        200 -> {
                            insertOrderWithReplace(order)
                            responseLiveData.postValue(Event.success(response.body()))
                        }
                        else -> {
                            val gson = Gson()
                            val jsonObject: JsonObject = gson.fromJson(response.errorBody()?.string(), JsonObject::class.java)
                            responseLiveData.postValue(Event.error(CourierNetworkException(jsonObject["message"].asString)))
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    responseLiveData.postValue(Event.error(t))
                }

            })
        }
    }

    //TODO offline
    fun decline(orderLiveData: MutableLiveData<Order>, responseLiveData: MutableLiveData<Event<ResponseBody>>) {
        responseLiveData.postValue(Event.loading())
        orderLiveData.value?.let {
            NetworkRepository.orderApi().acceptOrder(it.id).enqueue(object: Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    when (response.code()) {
                        200 -> responseLiveData.postValue(Event.success(response.body()))
                        else -> {
                            val gson = Gson()
                            val jsonObject: JsonObject = gson.fromJson(response.errorBody()?.string(), JsonObject::class.java)
                            responseLiveData.postValue(Event.error(CourierNetworkException(jsonObject["message"].asString)))
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    responseLiveData.postValue(Event.error(t))
                }

            })
        }
    }

    fun insertOrderWithReplace(order: Order) {
        //TODO uptodate only for order?
        db.customerDao.insertWithReplace(order.customer)
        Log.d("OrderService", "customer")
        db.productDao.insertAll(order.ordered.map { it.product })
        Log.d("OrderService", "products")
        db.orderDao.insertWithReplace(order)
        Log.d("OrderService", "order")
        db.orderedDao.insertAll(order.ordered.toList())
        Log.d("OrderService", "ordered")
        db.changeDao.setUpToDate("orders", order.id)
        Log.d("OrderService", "change")
    }

}