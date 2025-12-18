package com.coursework.featureSignup.verifyEmailScreen.presentation

import androidx.lifecycle.ViewModel
import com.coursework.corePresentation.navigation.AppRouter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class VerifyEmailViewModel(
    private val appRouter: AppRouter,
) : ViewModel(), VerifyEmailUiCallbacks {

    private val _uiState = MutableStateFlow(VerifyEmailViewState())
    val uiState = _uiState.asStateFlow()

    override fun onBackClick() {
        appRouter.pop()
    }

    override fun onCodeType(value: String) {
        _uiState.update {
            it.copy(
                codeInput = value
            )
        }
    }

    override fun onRegisterClick() {
        // TODO: Not yet implemented
    }
}