package com.coursework.corePresentation.shared.password

import androidx.compose.runtime.Immutable

@Immutable
data class PasswordRulesState(
    val isRule1Error: Boolean,
    val isRule2Error: Boolean,
    val isRule3Error: Boolean,
) {
    val isPasswordError: Boolean = isRule1Error || isRule2Error || isRule3Error
}

fun validatePasswordRules(password: String): PasswordRulesState {
    if (password.isEmpty()) {
        return PasswordRulesState(
            isRule1Error = false,
            isRule2Error = false,
            isRule3Error = false,
        )
    }

    val hasUpperCase = password.any { it.isUpperCase() }
    val hasLowerCase = password.any { it.isLowerCase() }
    val digitsCount = password.count { it.isDigit() }
    val hasSpecialCharacter = password.contains(Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]"))

    return PasswordRulesState(
        isRule1Error = hasUpperCase.not() || hasLowerCase.not(),
        isRule2Error = digitsCount < 2,
        isRule3Error = hasSpecialCharacter.not(),
    )
}

fun passwordsMismatch(
    password: String,
    confirmPassword: String
): Boolean =
    password.isNotEmpty() &&
        confirmPassword.isNotEmpty() &&
        password != confirmPassword

