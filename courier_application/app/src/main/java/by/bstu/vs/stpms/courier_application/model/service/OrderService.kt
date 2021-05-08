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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.mapstruct.factory.Mappers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object OrderService {
    private final val TAG = "OrderService"
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
                                CoroutineScope(Dispatchers.IO).launch {
                                    val mapper = Mappers.getMapper(OrderMapper::class.java)
                                    val orders = mapper.dtosToEntities(response.body())
                                    //Обновление локальной базы данных
                                    //Очистка записей связанных с заказами
                                    db.orderedDao.truncate()
                                    db.orderDao.truncate()
                                    //Вставка актуальной информации о активных заказах в локальную бд
                                    for (order in orders) {
                                        insertOrderWithReplaceToLocalDb(order)
                                    }
                                    //Получение списка активных заказов из локальной бд
                                    ordersLiveData.postValue(Event.success(getActiveOrdersFromLocalDb()))
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
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                //Получение списка активных заказов из локальной бд
                ordersLiveData.postValue(Event.success(getActiveOrdersFromLocalDb()))
            }
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
                            CoroutineScope(Dispatchers.IO).launch {
                                insertOrderWithReplaceToLocalDb(order)
                                responseLiveData.postValue(Event.success(response.body()))
                            }
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
    suspend fun decline(orderLiveData: MutableLiveData<Order>, responseLiveData: MutableLiveData<Event<ResponseBody>>) {
        orderLiveData.value?.let {
            //Удаляем запись о заказе из локальной бд
            //При удалении в таблице changes появится соответствующая запись, при возобновлении соединения,
            //по этой записи можно будет отменить заказ на сервере для синхронизации
            deleteOrderAndOrderedFromLocalDb(it)
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
            } else {
                responseLiveData.postValue(Event.success(null))
            }
        }
    }

    suspend fun updateState(orderLiveData: MutableLiveData<Order>, responseLiveData: MutableLiveData<Event<ResponseBody>>) {
        orderLiveData.value?.let {
            val nextState = it.state.next

            db.orderDao.updateState(it.id, nextState.name)

            if (isOnline(context)) {
                responseLiveData.postValue(Event.loading())
                NetworkRepository.orderApi().updateState(it.id, nextState.name).enqueue(object: Callback<ResponseBody>{
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
            } else {
                responseLiveData.postValue(Event.success(null))
            }
        }
    }


    //Метод вызывается при появлении соединения для отправки информации о заказах, от которых курьер отказался
    suspend fun sendDecline() {
        //Получаем список ID заказов, которые были удалены из таблицы заказов во время оффлайн использования
        val declinedOrdersIdList = db.changeDao.findAllByTableAndOperation("orders", "delete").map { it.itemId }
        Log.d(TAG, "sendDecline: declined in offline - $declinedOrdersIdList")
        for (id in declinedOrdersIdList) {
            //Отправляем соответствующий отмене заказа запрос на сервер
            NetworkRepository.orderApi().declineOrder(id).enqueue(object: Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.d(TAG, "sendDecline status: " + response.code())
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d(TAG, "sendDecline error: " + t.message)
                }

            })
            //Удаляем запись в таблице изменений для предотвращения последующего повторения
            db.changeDao.deleteByTableAndItemId("orders", id)
        }
    }

    //Метод вызывается при появлении соединения для отправки информации о заказах, состояние которых было обновлено курьером
    suspend fun sendUpdateState() {
        //Получаем список заказов, состояние которых было обновлено во время оффлайн использования
        val updateStateOrdersIdList = db.changeDao.findAllByTableAndOperation("orders", "update").map { it.itemId }
        Log.d(TAG, "sendUpdateState: updated in offline - $updateStateOrdersIdList")
        for (id in updateStateOrdersIdList) {
            //Получаем состояние заказа из БД по id
            val state = db.orderDao.getStateById(id)
            //Отправляем соответствующий обновлению состояния заказа запрос на сервер
            NetworkRepository.orderApi().updateState(id, state).enqueue(object: Callback<ResponseBody>{
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.d(TAG, "sendUpdate status: " + response.code())
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d(TAG, "sendUpdate error: " + t.message)
                }

            })
            //Удаляем запись в таблице изменений для предотвращения последующего повторения
            db.changeDao.deleteByTableAndItemId("orders", id)
        }
    }

    private suspend fun insertOrderWithReplaceToLocalDb(order: Order) {
        db.customerDao.insertWithReplace(order.customer)
        Log.d("OrderService", "customer")
        db.productDao.insertAll(order.ordered.map { it.product })
        Log.d("OrderService", "products $order")
        db.orderDao.insert(order)
        Log.d("OrderService", "order")
        db.orderedDao.insertAll(order.ordered.toList())
        Log.d("OrderService", "ordered")
        db.changeDao.setUpToDate("orders", order.id)
        Log.d("OrderService", "change")
    }

    private suspend fun deleteOrderAndOrderedFromLocalDb(order: Order) {
        db.orderedDao.deleteAll(order.ordered)
        db.orderDao.delete(order)
    }

    private suspend fun fillOrderFromDb(order: Order) {
        order.customer = db.customerDao.findById(order.customerId)
        val orderedList = db.orderedDao.findByOrderId(order.id)
        for (ordered in orderedList) {
            val product = db.productDao.findById(ordered.productId)
            ordered.product = product
        }
        order.ordered = orderedList

    }

    private suspend fun getActiveOrdersFromLocalDb(): List<Order> {
        //Получаем активные заказы из локальной бд
        val orders = db.orderDao.getAll()
        for (order in orders) {
            fillOrderFromDb(order)
        }
        return orders
    }

}