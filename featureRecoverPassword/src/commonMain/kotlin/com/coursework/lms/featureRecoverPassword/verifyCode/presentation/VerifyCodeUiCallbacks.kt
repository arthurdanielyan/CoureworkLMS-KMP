package com.coursework.lms.featureRecoverPassword.verifyCode.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal interface VerifyCodeUiCallbacks {

    fun onBackClick()
    fun onCodeType(value: String)
    fun onNextClick()
}


