package com.coursework.data.tokens

import android.content.Context
import org.koin.dsl.module

internal actual val tokenDataStoreModule = module {
    single {
        createTokenDataStore(get<Context>().filesDir.resolve(TOKENS_FILE_NAME).absolutePath)
    }
}
