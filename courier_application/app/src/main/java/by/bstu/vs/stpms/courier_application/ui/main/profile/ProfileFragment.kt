package by.bstu.vs.stpms.courier_application.ui.main.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.databinding.FragmentAuthBinding
import by.bstu.vs.stpms.courier_application.databinding.FragmentProfileBinding
import by.bstu.vs.stpms.courier_application.model.util.event.Status
import by.bstu.vs.stpms.courier_application.ui.login.AuthFragmentDirections
import java.time.Duration

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment!!.navController


        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.apply {
            vm = profileViewModel
            fragment = this@ProfileFragment
            lifecycleOwner = this@ProfileFragment
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
    }

    fun initViewModel() {
        val refresh = requireView().findViewById<SwipeRefreshLayout>(R.id.profile_refresh)
        refresh.setOnRefreshListener {
            profileViewModel.getUser()
            profileViewModel.getStats()
        }

        profileViewModel.statsLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.ERROR -> {
                    Toast.makeText(context, "Error: " + it.t?.message, Toast.LENGTH_SHORT).show()
                    refresh.isRefreshing = false
                }
                Status.SUCCESS -> {
                    refresh.isRefreshing = false
                    Log.d("HTTP", "profile: success")
                }
                Status.LOADING -> {
                    refresh.visibility = View.VISIBLE
                    Log.d("HTTP", "profile: loading")
                }
            }
        })

        profileViewModel.getUser()
        profileViewModel.getStats()
    }

    fun logout() {
        profileViewModel.logout()
        val action = ProfileFragmentDirections.actionNavigationProfileToNavigationAuth()
        navController.navigate(action)
    }
}