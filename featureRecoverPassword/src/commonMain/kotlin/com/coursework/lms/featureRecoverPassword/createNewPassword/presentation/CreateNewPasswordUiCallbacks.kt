package com.coursework.lms.featureRecoverPassword.createNewPassword.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal interface CreateNewPasswordUiCallbacks {

    fun onPasswordType(value: String)
    fun onPasswordConfirmType(value: String)
    fun onBackClick()
    fun onNextClick()
}