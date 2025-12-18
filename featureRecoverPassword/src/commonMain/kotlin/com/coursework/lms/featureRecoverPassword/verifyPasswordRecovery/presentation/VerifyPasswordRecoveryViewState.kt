package com.coursework.lms.featureRecoverPassword.verifyPasswordRecovery.presentation

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.StringValue

@Immutable
internal data class VerifyPasswordRecoveryViewState(
    val initialTimeRemaining: Int = 2 * 60 * 1000,
    val emailInput: String = "",
    val emailMessageError: StringValue? = null,
) {

    val isNextButtonEnabled = emailInput.isNotBlank()
            && emailMessageError ==  null
}