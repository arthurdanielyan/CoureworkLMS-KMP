package com.coursework.featureSignup.verifyEmailScreen.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal interface VerifyEmailUiCallbacks {

    fun onBackClick()
    fun onCodeType(value: String)
    fun onRegisterClick()
}