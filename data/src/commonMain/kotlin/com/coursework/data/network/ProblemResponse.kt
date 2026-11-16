package com.coursework.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProblemResponse(
    @SerialName("code") val code: String? = null,
    @SerialName("detail") val detail: String? = null,
    @SerialName("errors") val errors: List<FieldErrorResponse> = emptyList(),
)

@Serializable
internal data class FieldErrorResponse(
    @SerialName("field") val field: String,
    @SerialName("message") val message: String,
)
