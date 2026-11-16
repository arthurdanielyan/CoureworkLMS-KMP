package com.coursework.domain.login

import com.coursework.domain.auth.AuthRepository
import com.coursework.domain.user.UserRepository
import com.coursework.domain.user.usecases.rememberUserType

class LoginUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(
        email: String,
        password: String,
    ): Result<Unit> {
        return authRepository.login(email, password).fold(
            onSuccess = { userRepository.rememberUserType() },
            onFailure = { Result.failure(it) },
        )
    }
}
