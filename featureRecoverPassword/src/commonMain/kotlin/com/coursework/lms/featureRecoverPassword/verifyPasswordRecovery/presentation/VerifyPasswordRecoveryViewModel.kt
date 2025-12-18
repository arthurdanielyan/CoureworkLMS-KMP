package com.coursework.lms.featureRecoverPassword.verifyPasswordRecovery.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.lms.featureRecoverPassword.RecoverPasswordDestination
import com.coursework.lms.featureRecoverPassword.verifyCode.VerifyCodeDestination
import com.coursework.utils.stateInWhileSubscribed
import commonResources.invalid_email_message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import commonResources.Res.string as CoreStrings

internal class VerifyPasswordRecoveryViewModel(
    destination: RecoverPasswordDestination,
    private val appRouter: AppRouter,
) : ViewModel(), VerifyPasswordRecoveryUiCallbacks {

    private val initialTimeRemaining = MutableStateFlow(2*60*1000)
    private val emailInput = MutableStateFlow(destination.email)

    val uiState = combine(
        initialTimeRemaining,
        emailInput
    ) { timeRemaining, emailInput ->

        val emailMessageError = StringValue.StringResource(CoreStrings.invalid_email_message)
            .takeIf { emailInput.isNotBlank() && isEmailValid(emailInput).not() }

        VerifyPasswordRecoveryViewState(
            initialTimeRemaining = timeRemaining,
            emailInput = emailInput,
            emailMessageError = emailMessageError,
        )
    }.stateInWhileSubscribed(viewModelScope, VerifyPasswordRecoveryViewState())

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }

    override fun onCodeInput(value: String) {
        emailInput.update { value }
    }

    override fun onNextClick() {
        appRouter.navigate(VerifyCodeDestination)
    }

    override fun onBackClick() {
        appRouter.pop()
    }
}