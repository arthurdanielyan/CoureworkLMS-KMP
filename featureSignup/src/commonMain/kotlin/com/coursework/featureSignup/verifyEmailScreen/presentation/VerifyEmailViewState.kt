package com.coursework.featureSignup.verifyEmailScreen.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal data class VerifyEmailViewState(
    val codeInput: String = "",
    val isLoading: Boolean = false,
) {
    val isRegisterButtonEnabled = codeInput.length == CODE_LENGTH && isLoading.not()

    val isResendButtonEnabled = isLoading.not()

    companion object {
        const val CODE_LENGTH = 6
    }
}
