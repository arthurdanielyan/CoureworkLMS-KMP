package com.coursework.data.network

import com.coursework.data.tokens.TokenRefresher
import com.coursework.data.tokens.TokenStore
import com.coursework.domain.error.ApiException
import com.coursework.domain.error.NetworkException
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val NO_AUTH_CLIENT = "no_auth"

internal val httpClientModule = module {
    single {
        HttpClient {
            baseConfig()
            installAuth(
                tokenStore = get(),
                tokenRefresher = get(),
            )
        }
    }

    single(named(NO_AUTH_CLIENT)) {
        HttpClient {
            baseConfig()
        }
    }
}

private fun HttpClientConfig<*>.installAuth(
    tokenStore: TokenStore,
    tokenRefresher: TokenRefresher,
) {
    install(Auth) {
        bearer {
            loadTokens {
                tokenStore.getTokens()?.let { BearerTokens(it.accessToken, it.refreshToken) }
            }
            refreshTokens {
                tokenRefresher.refresh()?.let { BearerTokens(it.accessToken, it.refreshToken) }
            }
        }
    }
}

private fun HttpClientConfig<*>.baseConfig() {
    expectSuccess = true

    // Signup and resend wait for the backend to send an email, which takes several seconds on mobile data.
    install(HttpTimeout) {
        connectTimeoutMillis = 5_000
        socketTimeoutMillis = 30_000
        requestTimeoutMillis = 30_000
    }

    defaultRequest {
        url(serverUrl)
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            },
        )
    }

    HttpResponseValidator {
        handleResponseExceptionWithRequest { cause, _ ->
            when (cause) {
                is ResponseException -> throw cause.toApiException()
                is IOException -> throw NetworkException(cause)
            }
        }
    }
}

private suspend fun ResponseException.toApiException(): ApiException {
    val problem = runCatching { response.body<ProblemResponse>() }.getOrNull()
    return ApiException(
        status = response.status.value,
        code = problem?.code,
        detail = problem?.detail,
        fieldErrors = problem?.errors.orEmpty().associate { it.field to it.message },
    )
}
