package com.coursework.featureSignup.createAccountScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.LoginDestination
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.corePresentation.viewState.toErrorMessage
import com.coursework.domain.signup.StartRegistrationUseCase
import com.coursework.featureSignup.verifyEmailScreen.VerifyEmailDestination
import com.coursework.utils.stateInWhileSubscribed
import commonResources.invalid_email_message
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import commonResources.Res.string as CoreStrings

class CreateAccountViewModel(
    private val appRouter: AppRouter,
    private val startRegistrationUseCase: StartRegistrationUseCase,
) : ViewModel(), CreateAccountUiCallbacks {

    private val emailInput = MutableStateFlow("")
    private val fullNameInput = MutableStateFlow("")
    private val passwordInput = MutableStateFlow("")
    private val passwordConfirmInput = MutableStateFlow("")
    private val isLoading = MutableStateFlow(false)

    private val _errorMessages = Channel<StringValue>(
        capacity = Channel.BUFFERED,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val errorMessages = _errorMessages.receiveAsFlow()

    val uiState = combine(
        emailInput,
        fullNameInput,
        passwordInput,
        passwordConfirmInput,
        isLoading,
    ) { emailInput, fullNameInput, passwordInput, passwordConfirmInput, isLoading ->

        val emailMessageError = StringValue.StringResource(CoreStrings.invalid_email_message)
            .takeIf { emailInput.isNotBlank() && isEmailValid(emailInput).not() }

        CreateAccountViewState(
            emailInput = emailInput,
            emailErrorMessage = emailMessageError,
            fullNameInput = fullNameInput,
            passwordInput = passwordInput,
            passwordConfirmInput = passwordConfirmInput,
            isLoading = isLoading,
        )
    }.stateInWhileSubscribed(viewModelScope, CreateAccountViewState())

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.trim().matches(emailRegex.toRegex())
    }

    override fun onEmailType(value: String) {
        emailInput.update { value }
    }

    override fun onFullNameType(value: String) {
        fullNameInput.update { value }
    }

    override fun onPasswordType(value: String) {
        passwordInput.update { value }
    }

    override fun onPasswordConfirmType(value: String) {
        passwordConfirmInput.update { value }
    }

    override fun onNextClick() {
        if (isLoading.value) return
        val email = emailInput.value.trim()
        viewModelScope.launch {
            isLoading.value = true
            startRegistrationUseCase(
                email = email,
                fullName = fullNameInput.value.trim(),
                password = passwordInput.value,
            ).onSuccess {
                appRouter.navigate(VerifyEmailDestination(email = email))
            }.onFailure { error ->
                _errorMessages.trySend(error.toErrorMessage())
            }
            isLoading.value = false
        }
    }

    override fun onLoginClick() {
        appRouter.navigate(
            destination = LoginDestination,
            popAll = true,
        )
    }
}
