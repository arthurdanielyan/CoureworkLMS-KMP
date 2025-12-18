package com.coursework.lms.featureRecoverPassword.verifyCode.presentation

import androidx.lifecycle.ViewModel
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.lms.featureRecoverPassword.createNewPassword.CreateNewPasswordDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class VerifyCodeViewModel(
    private val appRouter: AppRouter,
) : ViewModel(), VerifyCodeUiCallbacks {

    private val _uiState = MutableStateFlow(VerifyCodeViewState())
    val uiState = _uiState.asStateFlow()

    override fun onBackClick() {
        appRouter.pop()
    }

    override fun onCodeType(value: String) {
        _uiState.update {
            it.copy(codeInput = value)
        }
    }

    override fun onNextClick() {
        appRouter.navigate(CreateNewPasswordDestination)
    }
}


