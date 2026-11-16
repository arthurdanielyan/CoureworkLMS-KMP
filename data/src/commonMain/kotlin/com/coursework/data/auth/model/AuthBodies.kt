package com.coursework.data.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class StartRegistrationBody(
    @SerialName("email") val email: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("password") val password: String,
)

@Serializable
internal data class ResendCodeBody(
    @SerialName("email") val email: String,
)

@Serializable
internal data class VerifyCodeBody(
    @SerialName("email") val email: String,
    @SerialName("code") val code: String,
)

@Serializable
internal data class LoginBody(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
)

@Serializable
internal data class RefreshTokenBody(
    @SerialName("refresh_token") val refreshToken: String,
)
