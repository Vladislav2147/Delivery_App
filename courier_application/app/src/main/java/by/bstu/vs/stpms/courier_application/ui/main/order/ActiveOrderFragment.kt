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
import by.bstu.vs.stpms.courier_application.model.database.entity.enums.OrderState
import by.bstu.vs.stpms.courier_application.model.util.event.Status
import by.bstu.vs.stpms.courier_application.ui.util.OrderAdapter
import su.j2e.rvjoiner.JoinableAdapter
import su.j2e.rvjoiner.JoinableLayout
import su.j2e.rvjoiner.RvJoiner

class ActiveOrderFragment : Fragment() {
    private lateinit var activeOrderViewModel: ActiveOrderViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderedAdapter: OrderAdapter
    private lateinit var deliveringAdapter: OrderAdapter
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

        val adapterOnClickListener = object : OrderAdapter.OnClickListener {
            override fun onVariantClick(order: Order?) {
                val action = ActiveOrderFragmentDirections.actionNavigationActiveOrderToNavigationActiveDetails(order!!)
                navController.navigate(action)
            }
        }
        orderedAdapter = OrderAdapter(requireContext()).apply {
            onClickListener = adapterOnClickListener
        }
        deliveringAdapter = OrderAdapter(requireContext()).apply {
            onClickListener = adapterOnClickListener
        }

        val joiner = RvJoiner()
        joiner.add(JoinableLayout(R.layout.label_delivering_layout))
        joiner.add(JoinableAdapter(deliveringAdapter))
        joiner.add(JoinableLayout(R.layout.label_ordered_layout))
        joiner.add(JoinableAdapter(orderedAdapter))

        recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_orders).apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context)
            adapter = joiner.adapter
        }

    }

    private fun initViewModel() {
        val refresh = requireView().findViewById<SwipeRefreshLayout>(R.id.orders_refresh)
        refresh.setOnRefreshListener {
            activeOrderViewModel.getActiveOrders()
        }
        activeOrderViewModel.ordersLiveData.observe(viewLifecycleOwner) { orders ->
            val deliveringOrders = orders.data?.filter { it.state == OrderState.Delivering }
            val orderedOrders = orders.data?.filter { it.state == OrderState.Ordered }
            deliveringAdapter.setOrders(deliveringOrders)
            orderedAdapter.setOrders(orderedOrders)
        }
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