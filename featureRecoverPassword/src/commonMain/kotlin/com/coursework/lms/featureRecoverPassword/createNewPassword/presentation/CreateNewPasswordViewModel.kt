package com.coursework.lms.featureRecoverPassword.createNewPassword.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.LoginDestination
import com.coursework.utils.stateInWhileSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

internal class CreateNewPasswordViewModel(
    private val appRouter: AppRouter,
) : ViewModel(), CreateNewPasswordUiCallbacks {

    private val passwordInput = MutableStateFlow("")
    private val passwordConfirmInput = MutableStateFlow("")

    val uiState = combine(
        passwordInput,
        passwordConfirmInput,
    ) { passwordInput, passwordConfirmInput ->
        CreateNewPasswordViewState(
            passwordInput = passwordInput,
            passwordConfirmInput = passwordConfirmInput,
        )
    }.stateInWhileSubscribed(viewModelScope, CreateNewPasswordViewState())

    override fun onPasswordType(value: String) {
        passwordInput.update { value }
    }

    override fun onPasswordConfirmType(value: String) {
        passwordConfirmInput.update { value }
    }

    override fun onBackClick() {
        appRouter.pop()
    }

    override fun onNextClick() {
        // TODO: integrate with reset password use case once available
        appRouter.navigate(
            destination = LoginDestination,
            popAll = true,
        )
    }
}