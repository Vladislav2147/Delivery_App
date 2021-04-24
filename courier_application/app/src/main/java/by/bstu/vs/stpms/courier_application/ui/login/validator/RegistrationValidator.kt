package by.bstu.vs.stpms.courier_application.ui.login.validator

object RegistrationValidator {

    private const val PHONE_PATTERN = "(^[+]\\d+(?:[ ]\\d+)*)"
    private const val EMAIL_PATTERN = "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$"

    fun isNameValid(name: String) = name.length in 2..50
    fun isPasswordValid(password: String) = password.length in 8..50
    fun isPhoneValid(phone: String) = phone.matches(Regex(PHONE_PATTERN))
    fun isEmailValid(email: String) = email.matches(Regex(EMAIL_PATTERN))
}