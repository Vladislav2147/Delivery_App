package by.bstu.vs.stpms.courier_application.ui.main.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.databinding.FragmentAvailableOrderDetailsBinding
import by.bstu.vs.stpms.courier_application.ui.util.OrderAdapter

class AvailableOrderDetailsFragment : Fragment() {

    private lateinit var viewModel: AvailableOrderDetailsViewModel
    private lateinit var orderAdapter: OrderAdapter
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

    }

}