package by.bstu.vs.stpms.courier_application.ui.main.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.databinding.FragmentAvailableOrderDetailsBinding
import by.bstu.vs.stpms.courier_application.model.util.event.Status
import by.bstu.vs.stpms.courier_application.ui.util.ProductAdapter
import com.google.android.material.button.MaterialButton


class AvailableOrderDetailsFragment : Fragment() {

    private lateinit var viewModel: AvailableOrderDetailsViewModel
    private lateinit var productAdapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewModel = ViewModelProvider(this).get(AvailableOrderDetailsViewModel::class.java)
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment!!.navController

        val binding = FragmentAvailableOrderDetailsBinding.inflate(inflater, container, false)
        binding.apply {
            vm = viewModel
            lifecycleOwner = this@AvailableOrderDetailsFragment
        }
        val order = AvailableOrderDetailsFragmentArgs.fromBundle(requireArguments()).availableOrder
        viewModel.orderLiveData.postValue(order)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.let {
            it.setDisplayShowCustomEnabled(false)
            it.setDisplayHomeAsUpEnabled(false)
        }

        initRecyclerView()
        initViewModel()
        initButtons()
    }

    private fun initRecyclerView() {
        productAdapter = ProductAdapter(requireContext())

        recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_available_orders_products).apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }

    }

    private fun initViewModel() {
        viewModel.orderLiveData.observe(viewLifecycleOwner) { order -> productAdapter.setProducts(order.ordered.toList()) }
    }

    private fun initButtons() {
        val callButton = requireView().findViewById<MaterialButton>(R.id.btn_details_call)
        val mapButton = requireView().findViewById<MaterialButton>(R.id.btn_details_map)

        callButton.setOnClickListener {
            val phone = viewModel.orderLiveData.value?.customer?.phone
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${phone}")
            startActivity(intent)
        }

        mapButton.setOnClickListener {
            val address = viewModel.orderLiveData.value?.address
            val gmmIntentUri =
                Uri.parse("geo:0,0?q=${address}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

}