package by.bstu.vs.stpms.courier_application.ui.login.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.databinding.FragmentAuthBinding
import by.bstu.vs.stpms.courier_application.model.retrofit.event.Status

class AuthFragment : Fragment() {

  private lateinit var authViewModel: AuthViewModel
  private lateinit var navController: NavController

  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    authViewModel =
            ViewModelProvider(this).get(AuthViewModel::class.java)

    val navHostFragment =
      requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
    navController = navHostFragment!!.navController

    val binding = FragmentAuthBinding.inflate(inflater, container, false)
    binding.apply {
      vm = authViewModel
      lifecycleOwner = this@AuthFragment
    }

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val progressBar = requireActivity().findViewById<ProgressBar>(R.id.login_progress)

    authViewModel.userLiveData.observe(viewLifecycleOwner, {
      when (it.status) {
        Status.ERROR -> {
          Toast.makeText(context, it.t?.message, Toast.LENGTH_SHORT).show()
          progressBar.visibility = View.INVISIBLE
        }
        Status.SUCCESS -> {
          progressBar.visibility = View.INVISIBLE
          Log.d("HTTP", "news: success")
          val action = AuthFragmentDirections.actionNavigationAuthToNavigationOrder(it.data!!.id!!)

          navController.navigate(action)
        }
        Status.LOADING -> {
          progressBar.visibility = View.VISIBLE
          Log.d("HTTP", "news: loading")
        }
      }
    })
  }
}