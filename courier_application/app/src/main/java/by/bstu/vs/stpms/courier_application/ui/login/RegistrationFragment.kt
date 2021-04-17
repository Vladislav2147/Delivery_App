package by.bstu.vs.stpms.courier_application.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.bstu.vs.stpms.courier_application.R

class RegistrationFragment : Fragment() {

  private lateinit var registrationViewModel: RegistrationViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    registrationViewModel =
            ViewModelProvider(this).get(RegistrationViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_registration, container, false)
    val textView: TextView = root.findViewById(R.id.text_dashboard)
    registrationViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })
    return root
  }
}