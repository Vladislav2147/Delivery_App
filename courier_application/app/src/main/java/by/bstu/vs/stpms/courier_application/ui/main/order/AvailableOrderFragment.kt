package by.bstu.vs.stpms.courier_application.ui.main.order

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.databinding.FragmentAvailableOrderBinding
import by.bstu.vs.stpms.courier_application.model.database.entity.Order
import by.bstu.vs.stpms.courier_application.model.util.event.Status
import by.bstu.vs.stpms.courier_application.ui.util.OrderAdapter

class AvailableOrderFragment : Fragment() {

    private lateinit var availableOrderViewModel: AvailableOrderViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        availableOrderViewModel =
                ViewModelProvider(this).get(AvailableOrderViewModel::class.java)
        val navHostFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment!!.navController

        val binding = FragmentAvailableOrderBinding.inflate(inflater, container, false)
        binding.apply {
            vm = availableOrderViewModel
            lifecycleOwner = this@AvailableOrderFragment
        }

        return binding.root
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
                val action = AvailableOrderFragmentDirections.actionNavigationAvailableOrderToNavigationAvailableOrderDetails(order!!)
                navController.navigate(action)
            }
        }

        recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_available_orders).apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }

    }

    private fun initViewModel() {
        val refresh = requireView().findViewById<SwipeRefreshLayout>(R.id.available_orders_refresh)
        refresh.setOnRefreshListener {
            availableOrderViewModel.getAvailableOrders()
        }
        availableOrderViewModel.ordersLiveData.observe(viewLifecycleOwner) { orders -> orderAdapter.setArticles(orders.data) }
        availableOrderViewModel.ordersLiveData.observe(viewLifecycleOwner, {
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

        availableOrderViewModel.getAvailableOrders()
    }
}
