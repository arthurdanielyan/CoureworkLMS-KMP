package com.coursework.data.auth

import com.coursework.data.auth.model.LoginBody
import com.coursework.data.auth.model.PendingRegistrationResponse
import com.coursework.data.auth.model.ResendCodeBody
import com.coursework.data.auth.model.StartRegistrationBody
import com.coursework.data.auth.model.TokenPairResponse
import com.coursework.data.auth.model.VerifyCodeBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class AuthApi(
    private val httpClient: HttpClient,
) {

    suspend fun startRegistration(body: StartRegistrationBody): PendingRegistrationResponse {
        return post("/api/v1/auth/registrations", body)
    }

    suspend fun resendCode(body: ResendCodeBody): PendingRegistrationResponse {
        return post("/api/v1/auth/registrations/resend", body)
    }

    suspend fun verifyCode(body: VerifyCodeBody): TokenPairResponse {
        return post("/api/v1/auth/registrations/verify", body)
    }

    suspend fun login(body: LoginBody): TokenPairResponse {
        return post("/api/v1/auth/login", body)
    }

    private suspend inline fun <reified B, reified R> post(
        path: String,
        body: B,
    ): R {
        return httpClient.post(path) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
    }
}
