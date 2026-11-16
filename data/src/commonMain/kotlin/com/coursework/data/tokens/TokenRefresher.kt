package com.coursework.data.tokens

import com.coursework.data.auth.model.RefreshTokenBody
import com.coursework.data.auth.model.TokenPairResponse
import com.coursework.domain.error.ApiException
import com.coursework.utils.runSuspendCatching
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

internal class TokenRefresher(
    private val httpClient: HttpClient,
    private val tokenStore: TokenStore,
) {

    suspend fun refresh(): TokenPair? {
        val current = tokenStore.getTokens() ?: return null
        return runSuspendCatching {
            httpClient.post("/api/v1/auth/refresh") {
                contentType(ContentType.Application.Json)
                setBody(RefreshTokenBody(refreshToken = current.refreshToken))
            }.body<TokenPairResponse>()
        }.fold(
            onSuccess = { response ->
                val newTokens = TokenPair(response.accessToken, response.refreshToken)
                // A login or logout may have landed while the refresh was in flight; don't bring the old session back.
                newTokens.takeIf { tokenStore.replace(current.refreshToken, it) }
            },
            onFailure = { error ->
                // The server rejected the refresh token, so the session is over. A network failure keeps it.
                if (error is ApiException) {
                    tokenStore.clear()
                }
                null
            },
        )
    }
}
