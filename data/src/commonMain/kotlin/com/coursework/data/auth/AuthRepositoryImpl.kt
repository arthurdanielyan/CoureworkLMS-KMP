package com.coursework.data.auth

import com.coursework.data.auth.model.LoginBody
import com.coursework.data.auth.model.ResendCodeBody
import com.coursework.data.auth.model.StartRegistrationBody
import com.coursework.data.auth.model.TokenPairResponse
import com.coursework.data.auth.model.VerifyCodeBody
import com.coursework.data.network.AuthTokenCache
import com.coursework.data.tokens.TokenPair
import com.coursework.data.tokens.TokenStore
import com.coursework.domain.auth.AuthRepository
import com.coursework.utils.runSuspendCatching

internal class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val tokenStore: TokenStore,
    private val authTokenCache: AuthTokenCache,
) : AuthRepository {

    override suspend fun startRegistration(
        email: String,
        fullName: String,
        password: String,
    ): Result<Unit> {
        return runSuspendCatching {
            authApi.startRegistration(
                StartRegistrationBody(
                    email = email,
                    fullName = fullName,
                    password = password,
                ),
            )
        }.map { }
    }

    override suspend fun resendVerificationCode(email: String): Result<Unit> {
        return runSuspendCatching {
            authApi.resendCode(ResendCodeBody(email = email))
        }.map { }
    }

    override suspend fun verifyEmail(
        email: String,
        code: String,
    ): Result<Unit> {
        return runSuspendCatching {
            startSession(authApi.verifyCode(VerifyCodeBody(email = email, code = code)))
        }
    }

    override suspend fun login(
        email: String,
        password: String,
    ): Result<Unit> {
        return runSuspendCatching {
            startSession(authApi.login(LoginBody(email = email, password = password)))
        }
    }

    private suspend fun startSession(tokens: TokenPairResponse) {
        tokenStore.save(TokenPair(tokens.accessToken, tokens.refreshToken))
        authTokenCache.clear()
    }
}
