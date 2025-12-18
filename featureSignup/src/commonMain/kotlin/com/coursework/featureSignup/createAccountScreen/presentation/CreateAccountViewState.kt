package com.coursework.featureSignup.createAccountScreen.presentation

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.StringValue

@Immutable
data class CreateAccountViewState(
    val emailInput: String = "",
    val emailErrorMessage: StringValue.StringResource? = null,
    val passwordInput: String = "",
    val passwordConfirmInput: String = "",
) {

    val isPasswordRule1Error: Boolean =
        passwordInput.isNotEmpty() && (!passwordInput.contains(Regex("[A-Z]"))
                || !passwordInput.contains(Regex("[a-z]")))

    val isPasswordRule2Error: Boolean =
        passwordInput.isNotEmpty() && passwordInput.count { it.isDigit() } < 2
    val isPasswordRule3Error: Boolean = passwordInput.isNotEmpty()
            && !passwordInput.contains(Regex("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]"))

    val isPasswordError: Boolean =
        isPasswordRule1Error || isPasswordRule2Error || isPasswordRule3Error

    val passwordsMatchError = passwordInput.isNotEmpty() && passwordConfirmInput.isNotEmpty() &&
            passwordInput != passwordConfirmInput

    val isEmailInputError = emailErrorMessage != null

    val isNextButtonEnabled = emailInput.isNotEmpty()
            && passwordInput.isNotEmpty()
            && passwordConfirmInput.isNotEmpty()
            && isEmailInputError.not()
            && isPasswordError.not()
            && passwordsMatchError.not()
}