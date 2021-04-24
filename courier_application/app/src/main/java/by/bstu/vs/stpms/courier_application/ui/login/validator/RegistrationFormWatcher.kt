package by.bstu.vs.stpms.courier_application.ui.login.validator

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import by.bstu.vs.stpms.courier_application.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegistrationFormWatcher(private val field: TextInputEditText): TextWatcher {

    override fun afterTextChanged(editable: Editable?) {
        val textInputLayout = field.parent.parent as TextInputLayout

        when (textInputLayout.id.toString()) {
            R.id.til_first_name.toString(), R.id.til_second_name.toString() -> {

            }
            R.id.til_email.toString() -> {

            }
            R.id.til_phone.toString() -> {

            }
            R.id.til_password.toString(), R.id.til_confirm_password.toString() -> {

            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
}