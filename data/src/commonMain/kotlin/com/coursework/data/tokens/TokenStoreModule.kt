package com.coursework.data.tokens

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.core.module.Module

internal expect val tokenDataStoreModule: Module

internal const val TOKENS_FILE_NAME = "tokens.preferences_pb"

internal fun createTokenDataStore(path: String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(produceFile = { path.toPath() })
}
