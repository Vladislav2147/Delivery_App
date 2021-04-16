package by.bstu.vs.stpms.courier_application.ui.login.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.model.retrofit.NetworkService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthFragment : Fragment() {

  private lateinit var authViewModel: AuthViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    authViewModel =
            ViewModelProvider(this).get(AuthViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_auth, container, false)
    val textView: TextView = root.findViewById(R.id.text_home)
    authViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })

    return root
  }
}