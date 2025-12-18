package com.coursework.featureLogin.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal interface LoginUiCallbacks {

    fun onEmailType(value: String)
    fun onPasswordType(value: String)
    fun onLoginClick()
    fun onSignUpClick()
    fun onLoginAsStudentClick()
    fun onLoginAsTeacherClick()
}