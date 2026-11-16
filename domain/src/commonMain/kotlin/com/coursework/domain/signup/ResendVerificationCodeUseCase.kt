package com.coursework.domain.signup

import com.coursework.domain.auth.AuthRepository

class ResendVerificationCodeUseCase(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(email: String): Result<Unit> {
        return authRepository.resendVerificationCode(email)
    }
}
