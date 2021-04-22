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
import by.bstu.vs.stpms.courier_application.model.database.contract.RoleType
import by.bstu.vs.stpms.courier_application.model.database.entity.Change
import by.bstu.vs.stpms.courier_application.model.database.entity.Product
import by.bstu.vs.stpms.courier_application.model.database.entity.Role
import by.bstu.vs.stpms.courier_application.model.database.entity.User
import by.bstu.vs.stpms.courier_application.model.network.NetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

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
        CoroutineScope(Dispatchers.IO).launch {
//            val product1 = Product().apply {
//                name = "prod1"
//                weight = 0.5
//                price = 2.0
//            }
//
//            val product2 = Product().apply {
//                id = 229
//                name = "prod2"
//                weight = 0.5
//                price = 2.0
//            }
//            CourierDatabase.getDatabase(NetworkService.context).productDao.insert(product1)
//            CourierDatabase.getDatabase(NetworkService.context).productDao.insert(product2)
//            CourierDatabase.getDatabase(NetworkService.context).productDao.delete(product2)
            val user = User().apply {
                firstName = "vladislav"
                secondName = "vladislavovich"
                email = "vlad@gmail.com"
                phone = "+375297729144"
                roles = setOf(Role(RoleType.ROLE_ADMIN), Role(RoleType.ROLE_BASIC))
            }

//            CourierDatabase.getDatabase(context).userDao.insert(user)

        }
        CourierDatabase.getDatabase(NetworkService.context).userDao.all.observe(viewLifecycleOwner, { list -> list.forEach {
            Toast.makeText(activity, it.roles.toString(), Toast.LENGTH_SHORT).show()
        } })
    }
}