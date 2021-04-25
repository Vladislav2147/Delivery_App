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
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository
import by.bstu.vs.stpms.courier_application.model.network.dto.OrderDto
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
        //TODO remove test code
//        CourierDatabase.getDatabase(NetworkRepository.context).roleDao.all.observe(viewLifecycleOwner, { list ->
//            Toast.makeText(activity, list.size.toString(), Toast.LENGTH_SHORT).show()
//        })
        NetworkRepository.orderApi().availableOrders.enqueue(object: Callback<List<OrderDto>> {
            override fun onResponse(
                call: Call<List<OrderDto>>,
                response: Response<List<OrderDto>>
            ) {
                val a = ""
            }

            override fun onFailure(call: Call<List<OrderDto>>, t: Throwable) {
                val b = ""
            }

        })
    }
}