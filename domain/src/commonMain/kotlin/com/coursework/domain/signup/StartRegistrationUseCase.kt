package com.coursework.domain.signup

import com.coursework.domain.auth.AuthRepository

class StartRegistrationUseCase(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(
        email: String,
        fullName: String,
        password: String,
    ): Result<Unit> {
        return authRepository.startRegistration(
            email = email,
            fullName = fullName,
            password = password,
        )
    }
}
