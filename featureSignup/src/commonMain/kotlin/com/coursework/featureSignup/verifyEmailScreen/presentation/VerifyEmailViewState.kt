package com.coursework.featureSignup.verifyEmailScreen.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal data class VerifyEmailViewState(
    val codeInput: String = "",
    val isRegistering: Boolean = false,
    val isResending: Boolean = false,
) {
    val isRegisterButtonEnabled = codeInput.length == CODE_LENGTH

    val isBusy = isRegistering || isResending

    companion object {
        const val CODE_LENGTH = 6
    }
}
