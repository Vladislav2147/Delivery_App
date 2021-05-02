package by.bstu.vs.stpms.courier_application.ui.main.order

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.model.database.entity.Order
import by.bstu.vs.stpms.courier_application.model.util.event.Status
import by.bstu.vs.stpms.courier_application.ui.util.OrderAdapter

class ActiveOrderFragment : Fragment() {
    private lateinit var activeOrderViewModel: ActiveOrderViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        activeOrderViewModel =
                ViewModelProvider(this).get(ActiveOrderViewModel::class.java)
        val navHostFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment!!.navController

        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
        initViewModel()
    }


    private fun initRecyclerView() {
        orderAdapter = OrderAdapter(requireContext())
        orderAdapter.onClickListener = object : OrderAdapter.OnClickListener {
            override fun onVariantClick(order: Order?) {
                //TODO fix
//                val action = AvailableOrderFragmentDirections.actionNavigationAvailableOrderToNavigationAvailableOrderDetails(order!!)
//                navController.navigate(action)
            }
        }

        recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_orders).apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }

    }

    private fun initViewModel() {
        val refresh = requireView().findViewById<SwipeRefreshLayout>(R.id.orders_refresh)
        refresh.setOnRefreshListener {
            activeOrderViewModel.getActiveOrders()
        }
        activeOrderViewModel.ordersLiveData.observe(viewLifecycleOwner) { orders -> orderAdapter.setOrders(orders.data) }
        activeOrderViewModel.ordersLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.ERROR -> {
                    Toast.makeText(context, "error " + it.t?.message, Toast.LENGTH_SHORT).show()
                    refresh.isRefreshing = false
                    recyclerView.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    refresh.isRefreshing = false
                    recyclerView.visibility = View.VISIBLE
                    Log.d("HTTP", "orders: success")
                }
                Status.LOADING -> {
                    refresh.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    Log.d("HTTP", "orders: loading")
                }
            }
        })

        activeOrderViewModel.getActiveOrders()
    }

}