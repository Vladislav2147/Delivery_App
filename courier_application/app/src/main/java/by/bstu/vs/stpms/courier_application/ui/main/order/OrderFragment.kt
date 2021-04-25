package by.bstu.vs.stpms.courier_application.ui.main.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.model.database.CourierDatabase
import by.bstu.vs.stpms.courier_application.model.database.entity.Product
import by.bstu.vs.stpms.courier_application.model.exception.CourierNetworkException
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository
import by.bstu.vs.stpms.courier_application.model.network.dto.OrderDto
import by.bstu.vs.stpms.courier_application.model.service.mapper.OrderMapper
import by.bstu.vs.stpms.courier_application.model.service.mapper.ProductMapper
import by.bstu.vs.stpms.courier_application.model.util.event.Event
import org.mapstruct.factory.Mappers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        orderViewModel =
                ViewModelProvider(this).get(OrderViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_order, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        orderViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        NetworkRepository.orderApi().availableOrders.enqueue(object: Callback<List<OrderDto>> {
            override fun onResponse(call: Call<List<OrderDto>>, response: Response<List<OrderDto>>) {
                try {
                    when (response.code()) {
                        //Успешное получение данных
                        200 -> {
                            val mapper = Mappers.getMapper(OrderMapper::class.java)
                            val orders = mapper.dtosToEntities(response.body())
                            val i = orders.size
//                            ordersLiveData.postValue(Event.success(response.body()))
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
//                    responseLiveData.postValue(Event.error(e))
                }
            }

            override fun onFailure(call: Call<List<OrderDto>>, t: Throwable) {
                val b = ""
            }

        })
    }
}