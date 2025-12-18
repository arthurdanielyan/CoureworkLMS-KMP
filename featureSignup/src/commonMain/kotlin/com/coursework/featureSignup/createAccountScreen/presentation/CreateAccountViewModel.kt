package com.coursework.featureSignup.createAccountScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.LoginDestination
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.featureSignup.verifyEmailScreen.VerifyEmailDestination
import com.coursework.utils.stateInWhileSubscribed
import commonResources.invalid_email_message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import commonResources.Res.string as CoreStrings

class CreateAccountViewModel(
    private val appRouter: AppRouter,
) : ViewModel(), CreateAccountUiCallbacks {

    private val emailInput = MutableStateFlow("")
    private val passwordInput = MutableStateFlow("")
    private val passwordConfirmInput = MutableStateFlow("")

    val uiState = combine(
        emailInput,
        passwordInput,
        passwordConfirmInput,
    ) { emailInput, passwordInput, passwordConfirmInput ->

        val emailMessageError = StringValue.StringResource(CoreStrings.invalid_email_message)
            .takeIf { emailInput.isNotBlank() && isEmailValid(emailInput).not() }

        CreateAccountViewState(
            emailInput = emailInput,
            emailErrorMessage = emailMessageError,
            passwordInput = passwordInput,
            passwordConfirmInput = passwordConfirmInput,
        )
    }.stateInWhileSubscribed(viewModelScope, CreateAccountViewState())

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }

    override fun onEmailType(value: String) {
        emailInput.update { value }
    }

    override fun onPasswordType(value: String) {
        passwordInput.update { value }
    }

    override fun onPasswordConfirmType(value: String) {
        passwordConfirmInput.update { value }
    }

    override fun onNextClick() {
        appRouter.navigate(VerifyEmailDestination)
    }

    override fun onLoginClick() {
        appRouter.navigate(
            destination = LoginDestination,
            popAll = true,
        )
    }
}