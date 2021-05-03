package by.bstu.vs.stpms.courier_application.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.com.sapereaude.maskedEditText.MaskedEditText
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.databinding.FragmentRegistrationBinding
import by.bstu.vs.stpms.courier_application.model.util.event.Status
import by.bstu.vs.stpms.courier_application.model.util.getAllChildren
import by.bstu.vs.stpms.courier_application.ui.login.validator.RegistrationFormWatcher
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class RegistrationFragment : Fragment() {

    private lateinit var registrationViewModel: RegistrationViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        registrationViewModel =
                ViewModelProvider(this).get(RegistrationViewModel::class.java)

        val navHostFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment!!.navController

        val binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        binding.apply {
            fragment = this@RegistrationFragment
            vm = registrationViewModel
            lifecycleOwner = this@RegistrationFragment
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val group = view?.findViewById(R.id.reg_form) as ViewGroup
        group
                .getAllChildren()
                .filter { it is EditText || it is MaskedEditText }
                .forEach {
                    (it as EditText).addTextChangedListener(RegistrationFormWatcher(it))
                }

        registrationViewModel.responseLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.ERROR -> {
                    view?.findViewById<MaterialButton>(R.id.btn_sign_up)?.isEnabled = true
                    Toast.makeText(context, it.t?.message, Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    Toast.makeText(context, "Sign up success", Toast.LENGTH_SHORT).show()
                    Log.d("HTTP", "sign up: success")
                    navController.popBackStack()

                }
                Status.LOADING -> {
                    view?.findViewById<MaterialButton>(R.id.btn_sign_up)?.isEnabled = false
                    Log.d("HTTP", "sign up: loading")
                }
            }
        })
    }


}