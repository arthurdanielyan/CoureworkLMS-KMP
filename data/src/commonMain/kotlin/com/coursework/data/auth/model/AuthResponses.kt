package com.coursework.data.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenPairResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
)

@Serializable
internal data class PendingRegistrationResponse(
    @SerialName("email") val email: String,
    @SerialName("code_expires_at") val codeExpiresAt: String,
    @SerialName("resend_available_at") val resendAvailableAt: String,
)
