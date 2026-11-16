package com.coursework.data.tokens

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

internal data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
)

internal class TokenStore(
    private val dataStore: DataStore<Preferences>,
) {

    suspend fun getTokens(): TokenPair? {
        val preferences = dataStore.data.first()
        val accessToken = preferences[ACCESS_TOKEN] ?: return null
        val refreshToken = preferences[REFRESH_TOKEN] ?: return null
        return TokenPair(accessToken, refreshToken)
    }

    suspend fun save(tokens: TokenPair) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = tokens.accessToken
            preferences[REFRESH_TOKEN] = tokens.refreshToken
        }
    }

    suspend fun replace(
        expectedRefreshToken: String,
        newTokens: TokenPair,
    ): Boolean {
        var replaced = false
        // updateData runs one write at a time, so the check and the write can't be split by a login or logout.
        dataStore.updateData { current ->
            if (current[REFRESH_TOKEN] != expectedRefreshToken) {
                return@updateData current
            }
            replaced = true
            current.toMutablePreferences().apply {
                this[ACCESS_TOKEN] = newTokens.accessToken
                this[REFRESH_TOKEN] = newTokens.refreshToken
            }
        }
        return replaced
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }

    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }
}
