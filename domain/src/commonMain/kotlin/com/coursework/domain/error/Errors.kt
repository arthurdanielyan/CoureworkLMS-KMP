package com.coursework.domain.error

class ApiException(
    val status: Int,
    val code: String?,
    val detail: String?,
    val fieldErrors: Map<String, String> = emptyMap(),
) : Exception(detail ?: code)

class NetworkException(
    cause: Throwable? = null,
) : Exception("Could not reach the server", cause)
