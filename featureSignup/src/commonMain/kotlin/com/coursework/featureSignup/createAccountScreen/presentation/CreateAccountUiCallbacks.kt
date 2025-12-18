package com.coursework.featureSignup.createAccountScreen.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal interface CreateAccountUiCallbacks {

    fun onEmailType(value: String)
    fun onPasswordType(value: String)
    fun onPasswordConfirmType(value: String)
    fun onNextClick()
    fun onLoginClick()
}