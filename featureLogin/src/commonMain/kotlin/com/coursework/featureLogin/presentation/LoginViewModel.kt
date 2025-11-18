package com.coursework.featureLogin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.LoginDestination
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.featureHome.HomeScreenDestination
import com.coursework.utils.stateInWhileSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import lms.featurelogin.generated.resources.invalid_email_message
import lms.featurelogin.generated.resources.Res.string as Strings

internal class LoginViewModel(
    private val appRouter: AppRouter,
) : ViewModel(), LoginUiCallbacks {

    private val emailInput = MutableStateFlow("")
    private val passwordInput = MutableStateFlow("")

    val uiState = combine(
        emailInput,
        passwordInput,
    ) { emailInput, passwordInput ->

        val emailMessageError = StringValue.StringResource(Strings.invalid_email_message)
            .takeIf { emailInput.isNotBlank() && isEmailValid(emailInput).not() }

        LoginViewState(
            emailInput = emailInput,
            passwordInput = passwordInput,
            emailErrorMessage = emailMessageError,
        )
    }.stateInWhileSubscribed(viewModelScope, LoginViewState())

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

    override fun onLoginClick() {
        // TODO: Not yet implemented
        appRouter.navigate(
            destination = HomeScreenDestination,
            popUpTo = LoginDestination::class,
            inclusive = true,
        )
    }

    override fun onLoginAsStudentClick() {
        // For Testing
        // TODO: Not yet implemented
    }

    override fun onLoginAsTeacherClick() {
        // For Testing
        appRouter.navigate(
            destination = HomeScreenDestination,
            popUpTo = LoginDestination::class,
            inclusive = true,
        )
    }
}