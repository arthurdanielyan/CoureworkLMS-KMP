package com.coursework.lms.featureRecoverPassword.createNewPassword.presentation

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.shared.password.PasswordRulesState
import com.coursework.corePresentation.shared.password.passwordsMismatch
import com.coursework.corePresentation.shared.password.validatePasswordRules

@Immutable
data class CreateNewPasswordViewState(
    val passwordInput: String = "",
    val passwordConfirmInput: String = "",
) {

    val passwordRulesState: PasswordRulesState
        get() = validatePasswordRules(passwordInput)

    val isPasswordError: Boolean = passwordRulesState.isPasswordError

    val passwordsMatchError: Boolean = passwordsMismatch(passwordInput, passwordConfirmInput)

    val isNextButtonEnabled = passwordInput.isNotEmpty()
            && passwordConfirmInput.isNotEmpty()
            && isPasswordError.not()
            && passwordsMatchError.not()
}