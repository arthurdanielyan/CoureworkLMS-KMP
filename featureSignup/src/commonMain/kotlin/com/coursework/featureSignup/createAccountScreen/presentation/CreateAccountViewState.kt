package com.coursework.featureSignup.createAccountScreen.presentation

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.shared.password.PasswordRulesState
import com.coursework.corePresentation.shared.password.passwordsMismatch
import com.coursework.corePresentation.shared.password.validatePasswordRules
import com.coursework.corePresentation.viewState.StringValue

@Immutable
data class CreateAccountViewState(
    val emailInput: String = "",
    val emailErrorMessage: StringValue.StringResource? = null,
    val fullNameInput: String = "",
    val passwordInput: String = "",
    val passwordConfirmInput: String = "",
    val isLoading: Boolean = false,
) {

    val passwordRulesState: PasswordRulesState
        get() = validatePasswordRules(passwordInput)

    val isPasswordRule1Error: Boolean = passwordRulesState.isRule1Error
    val isPasswordRule2Error: Boolean = passwordRulesState.isRule2Error
    val isPasswordRule3Error: Boolean = passwordRulesState.isRule3Error

    val isPasswordError: Boolean = passwordRulesState.isPasswordError

    val passwordsMatchError: Boolean = passwordsMismatch(passwordInput, passwordConfirmInput)

    val isEmailInputError = emailErrorMessage != null

    private val isFullNameValid = fullNameInput.trim().length in 2..100

    val isNextButtonEnabled = emailInput.isNotEmpty()
            && isFullNameValid
            && passwordInput.isNotEmpty()
            && passwordConfirmInput.isNotEmpty()
            && isEmailInputError.not()
            && isPasswordError.not()
            && passwordsMatchError.not()
}
