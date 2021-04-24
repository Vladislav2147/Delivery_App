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
                textInputLayout.error = if (RegistrationValidator.isNameValid(editable.toString())) {
                    null
                } else {
                    "Length should be between 2 and 50"
                }
            }
            R.id.til_email.toString() -> {
                textInputLayout.error = if (RegistrationValidator.isEmailValid(editable.toString())) {
                    null
                } else {
                    "Email should be valid"
                }
            }
            R.id.til_phone.toString() -> {
                textInputLayout.error = if (RegistrationValidator.isPhoneValid(editable.toString())) {
                    null
                } else {
                    "Phone should be valid"
                }

            }
            R.id.til_password.toString(), R.id.til_confirm_password.toString() -> {
                textInputLayout.error = if (RegistrationValidator.isPasswordValid(editable.toString())) {
                    null
                } else {
                    "Length should be between 8 and 64"
                }
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
}