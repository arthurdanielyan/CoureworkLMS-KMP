package com.coursework.featureSignup.verifyEmailScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.corePresentation.viewState.toErrorMessage
import com.coursework.domain.signup.ResendVerificationCodeUseCase
import com.coursework.domain.signup.VerifyEmailUseCase
import com.coursework.featureHome.HomeScreenDestination
import com.coursework.featureSignup.verifyEmailScreen.VerifyEmailDestination
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lms.featuresignup.generated.resources.code_resent
import lms.featuresignup.generated.resources.Res.string as Strings

internal class VerifyEmailViewModel(
    destination: VerifyEmailDestination,
    private val appRouter: AppRouter,
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val resendVerificationCodeUseCase: ResendVerificationCodeUseCase,
) : ViewModel(), VerifyEmailUiCallbacks {

    private val email = destination.email

    private val _uiState = MutableStateFlow(VerifyEmailViewState())
    val uiState = _uiState.asStateFlow()

    private val _messages = Channel<StringValue>(
        capacity = Channel.BUFFERED,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val messages = _messages.receiveAsFlow()

    override fun onBackClick() {
        appRouter.pop()
    }

    override fun onCodeType(value: String) {
        val code = value.filter { it.isDigit() }.take(VerifyEmailViewState.CODE_LENGTH)
        _uiState.update {
            it.copy(
                codeInput = code
            )
        }
    }

    override fun onRegisterClick() {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            setLoading(true)
            verifyEmailUseCase(
                email = email,
                code = _uiState.value.codeInput,
            ).onSuccess {
                appRouter.navigate(
                    destination = HomeScreenDestination,
                    popAll = true,
                )
            }.onFailure { error ->
                _messages.trySend(error.toErrorMessage())
            }
            setLoading(false)
        }
    }

    override fun onResendClick() {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {
            setLoading(true)
            resendVerificationCodeUseCase(email)
                .onSuccess {
                    _messages.trySend(StringValue.StringResource(Strings.code_resent))
                }
                .onFailure { error ->
                    _messages.trySend(error.toErrorMessage())
                }
            setLoading(false)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }
}
