package com.coursework.data.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AccountResponse(
    @SerialName("id") val id: Int,
    @SerialName("email") val email: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("role") val role: String,
    @SerialName("permissions") val permissions: List<String> = emptyList(),
)
