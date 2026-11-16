package com.coursework.featureLogin.presentation

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.StringValue

@Immutable
internal data class LoginViewState(
    val emailInput: String = "",
    val passwordInput: String = "",
    val emailErrorMessage: StringValue? = null,
    val isLoading: Boolean = false,
) {

    val isEmailInputError = emailErrorMessage != null

    val isLoginButtonEnabled = emailInput.isNotBlank()
            && passwordInput.isNotEmpty()
            && isEmailInputError.not()
            && isLoading.not()
}