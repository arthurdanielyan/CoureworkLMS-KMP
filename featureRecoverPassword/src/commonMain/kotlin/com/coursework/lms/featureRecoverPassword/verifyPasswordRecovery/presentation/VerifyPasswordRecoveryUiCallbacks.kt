package com.coursework.lms.featureRecoverPassword.verifyPasswordRecovery.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal interface VerifyPasswordRecoveryUiCallbacks {

    fun onCodeInput(value: String)
    fun onNextClick()
    fun onBackClick()
}