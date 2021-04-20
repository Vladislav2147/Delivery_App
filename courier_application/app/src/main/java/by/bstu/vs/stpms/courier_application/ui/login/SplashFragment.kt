package by.bstu.vs.stpms.courier_application.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.model.network.util.event.Status

class SplashFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        viewModel.userLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.ERROR -> {
                    val toLogin = SplashFragmentDirections.actionNavigationSplashToNavigationAuth()
                    navController.navigate(toLogin)
                }
                Status.SUCCESS -> {
                    val toOrders = SplashFragmentDirections.actionNavigationSplashToNavigationOrder(it.data!!.id!!)
                    navController.navigate(toOrders)
                }
                else -> { }
            }
        })
        return inflater.inflate(R.layout.fragment_spash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.tryAutoLogin()
    }

}