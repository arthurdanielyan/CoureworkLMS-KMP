package com.coursework.data.user

import com.coursework.data.user.model.AccountResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class UserApi(
    private val httpClient: HttpClient,
) {

    suspend fun getMe(): AccountResponse {
        return httpClient.get("/api/v1/me").body()
    }
}
