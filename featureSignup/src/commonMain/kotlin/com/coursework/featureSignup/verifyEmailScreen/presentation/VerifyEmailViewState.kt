package com.coursework.featureSignup.verifyEmailScreen.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal data class VerifyEmailViewState(
    val codeInput: String = "",
) {
    val isRegisterButtonEnabled = codeInput.isNotBlank()
}