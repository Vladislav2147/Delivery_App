package by.bstu.vs.stpms.courier_application.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.bstu.vs.stpms.courier_application.R
import by.bstu.vs.stpms.courier_application.databinding.FragmentRegistrationBinding
import by.bstu.vs.stpms.courier_application.ui.login.validator.RegistrationFormWatcher
import com.google.android.material.textfield.TextInputEditText


class RegistrationFragment : Fragment() {

  private lateinit var registrationViewModel: RegistrationViewModel

  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    registrationViewModel =
            ViewModelProvider(this).get(RegistrationViewModel::class.java)

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
            .filterIsInstance<TextInputEditText>()
            .forEach {
              (it as EditText).addTextChangedListener(RegistrationFormWatcher(it))
            }


    (activity as AppCompatActivity?)?.supportActionBar?.let {
      it.setDisplayShowCustomEnabled(false)
      it.setDisplayHomeAsUpEnabled(false)
    }
  }

  fun View.getAllChildren(): List<View> {
    val result = ArrayList<View>()
    if (this !is ViewGroup) {
      result.add(this)
    } else {
      for (index in 0 until this.childCount) {
        val child = this.getChildAt(index)
        result.addAll(child.getAllChildren())
      }
    }
    return result
  }
}