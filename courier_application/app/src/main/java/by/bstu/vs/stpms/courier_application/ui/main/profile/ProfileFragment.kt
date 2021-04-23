package by.bstu.vs.stpms.courier_application.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.databinding.FragmentAuthBinding
import by.bstu.vs.stpms.courier_application.databinding.FragmentProfileBinding
import by.bstu.vs.stpms.courier_application.ui.login.AuthFragmentDirections

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
            fragment = this@ProfileFragment
            lifecycleOwner = this@ProfileFragment
        }

        return binding.root
    }

    fun logout() {
        profileViewModel.logout()
        val action = ProfileFragmentDirections.actionNavigationProfileToNavigationAuth()
        navController.navigate(action)
    }
}