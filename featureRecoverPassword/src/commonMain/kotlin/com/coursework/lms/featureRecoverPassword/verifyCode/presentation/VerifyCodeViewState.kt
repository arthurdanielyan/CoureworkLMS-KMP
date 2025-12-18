package com.coursework.lms.featureRecoverPassword.verifyCode.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal data class VerifyCodeViewState(
    val codeInput: String = "",
) {
    val isNextButtonEnabled: Boolean = codeInput.isNotBlank()
}


