package com.coursework.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.authProvider
import io.ktor.client.plugins.auth.providers.BearerAuthProvider

internal class AuthTokenCache(
    private val httpClient: HttpClient,
) {

    // Ktor loads the tokens once and keeps them, so after a new login it would still send the previous session's.
    fun clear() {
        httpClient.authProvider<BearerAuthProvider>()?.clearToken()
    }
}
