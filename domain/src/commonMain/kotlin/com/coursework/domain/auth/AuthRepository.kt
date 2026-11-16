package com.coursework.domain.auth

interface AuthRepository {

    suspend fun startRegistration(
        email: String,
        fullName: String,
        password: String,
    ): Result<Unit>

    suspend fun resendVerificationCode(email: String): Result<Unit>

    suspend fun verifyEmail(
        email: String,
        code: String,
    ): Result<Unit>

    suspend fun login(
        email: String,
        password: String,
    ): Result<Unit>
}
