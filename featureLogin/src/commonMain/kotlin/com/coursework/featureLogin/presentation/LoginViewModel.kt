package com.coursework.featureLogin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.domain.login.LoginUseCase
import com.coursework.domain.user.model.UserType
import com.coursework.featureHome.HomeScreenDestination
import com.coursework.featureSignup.SingupDestination
import com.coursework.utils.stateInWhileSubscribed
import commonResources.invalid_email_message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import commonResources.Res.string as CoreStrings

internal class LoginViewModel(
    private val appRouter: AppRouter,
    private val loginUseCase: LoginUseCase,
) : ViewModel(), LoginUiCallbacks {

    private val emailInput = MutableStateFlow("")
    private val passwordInput = MutableStateFlow("")

    val uiState = combine(
        emailInput,
        passwordInput,
    ) { emailInput, passwordInput ->

        val emailMessageError = StringValue.StringResource(CoreStrings.invalid_email_message)
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
        viewModelScope.launch {
            loginUseCase(
                if (emailInput.value.contains("student")) { // Mock implementation
                    UserType.Student
                } else {
                    UserType.Teacher
                }
            ).onSuccess {
                appRouter.navigate(
                    destination = HomeScreenDestination,
                    popAll = true
                )
            }
        }
    }

    override fun onSignUpClick() {
        appRouter.navigate(
            destination = SingupDestination,
            popAll = true
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
            popAll = true
        )
    }
}