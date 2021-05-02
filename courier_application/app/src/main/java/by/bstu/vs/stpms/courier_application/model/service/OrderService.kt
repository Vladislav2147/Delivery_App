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
import by.bstu.vs.stpms.courier_application.model.util.livedata.observeOnce
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.mapstruct.factory.Mappers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderService {

    private val orderApi = NetworkRepository.orderApi()
    private val db: CourierDatabase = CourierDatabase.getDatabase(context)

    //Получение списка доступных заказов (только онлайн)
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

    //Получение списка активных заказов (доступно без подключения к интернету)
    fun getActiveOrders(ordersLiveData: MutableLiveData<Event<List<Order>>>) {
        ordersLiveData.postValue(Event.loading())
        //Если подключение к интернету есть
        if (isOnline(context)) {
            NetworkRepository.orderApi().activeOrders.enqueue(object: Callback<List<OrderDto>> {
                override fun onResponse(call: Call<List<OrderDto>>, response: Response<List<OrderDto>>) {
                    try {
                        when (response.code()) {
                            //Успешное получение данных
                            200 -> {
                                val mapper = Mappers.getMapper(OrderMapper::class.java)
                                val orders = mapper.dtosToEntities(response.body())
                                //Обновление локальной базы данных
                                for (order in orders) {
                                    insertOrderWithReplaceToLocalDb(order)
                                }
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
        //Получаем активные заказы из локальной бд
        db.orderDao.all.observeOnce { orders ->
            for (order in orders) {
                fillOrderFromDb(order)
            }
            ordersLiveData.postValue(Event.success(orders))
        }
    }

    //Принять заказ (только онлайн)
    fun accept(orderLiveData: MutableLiveData<Order>, responseLiveData: MutableLiveData<Event<ResponseBody>>) {
        responseLiveData.postValue(Event.loading())
        orderLiveData.value?.let {  order ->
            NetworkRepository.orderApi().acceptOrder(order.id).enqueue(object: Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    when (response.code()) {
                        200 -> {
                            insertOrderWithReplaceToLocalDb(order)
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

    //Отмена заказа (доступно без подключения к интернету)
    fun decline(orderLiveData: MutableLiveData<Order>, responseLiveData: MutableLiveData<Event<ResponseBody>>) {
        orderLiveData.value?.let {
            //Удаляем запись о заказе из локальной бд
            //При удалении в таблице changes появится соответствующая запись, при возобновлении соединения,
            //по этой записи можно будет отменить заказ на сервере для синхронизации
            deleteOrderFromLocalDb(it)
            //Если есть интернет, то выполняем отмену заказа и на сервере
            if (isOnline(context)) {
                responseLiveData.postValue(Event.loading())
                NetworkRepository.orderApi().declineOrder(it.id).enqueue(object: Callback<ResponseBody>{
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
    }

    fun insertOrderWithReplaceToLocalDb(order: Order) {
        //TODO uptodate only for order?
        db.customerDao.insertWithReplace(order.customer)
        Log.d("OrderService", "customer")
        db.productDao.insertAll(order.ordered.map { it.product })
        Log.d("OrderService", "products $order")
        db.orderDao.insertWithReplace(order)
        Log.d("OrderService", "order")
        db.orderedDao.insertAll(order.ordered.toList())
        Log.d("OrderService", "ordered")
        db.changeDao.setUpToDate("orders", order.id)
        Log.d("OrderService", "change")
    }

    private fun deleteOrderFromLocalDb(order: Order) {
        db.orderedDao.deleteAll(order.ordered)
        db.orderDao.delete(order)
    }

    private fun fillOrderFromDb(order: Order) {
        CoroutineScope(Dispatchers.IO).launch {
            db.customerDao.findById(order.customerId).observeOnce { customer ->
                order.customer = customer
            }
            db.orderedDao.findByOrderId(order.id).observeOnce { orderedList ->
                for (ordered in orderedList) {
                    db.productDao.findById(ordered.productId).observeOnce { product ->
                        ordered.product = product
                    }
                }
                order.ordered = orderedList
            }
        }
    }

}