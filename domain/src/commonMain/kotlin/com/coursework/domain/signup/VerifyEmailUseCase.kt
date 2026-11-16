package com.coursework.domain.signup

import com.coursework.domain.auth.AuthRepository
import com.coursework.domain.user.UserRepository
import com.coursework.domain.user.usecases.rememberUserType

class VerifyEmailUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(
        email: String,
        code: String,
    ): Result<Unit> {
        return authRepository.verifyEmail(email, code).fold(
            onSuccess = { userRepository.rememberUserType() },
            onFailure = { Result.failure(it) },
        )
    }
}
