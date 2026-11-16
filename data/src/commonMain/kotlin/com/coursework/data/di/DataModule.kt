package com.coursework.data.di

import com.coursework.data.auth.AuthApi
import com.coursework.data.auth.AuthRepositoryImpl
import com.coursework.data.bookDetails.DummyBookDetailsRepository
import com.coursework.data.bookDetails.mapper.NamedItemResponseMapper
import com.coursework.data.bookDetails.mapper.ReserveBookResponseMapper
import com.coursework.data.books.DummyBooksRepository
import com.coursework.data.network.AuthTokenCache
import com.coursework.data.network.NO_AUTH_CLIENT
import com.coursework.data.network.httpClientModule
import com.coursework.data.tokens.TokenRefresher
import com.coursework.data.tokens.TokenStore
import com.coursework.data.tokens.tokenDataStoreModule
import com.coursework.data.user.UserApi
import com.coursework.data.user.UserRepositoryImpl
import com.coursework.data.user.mapper.UserTypeMapper
import com.coursework.domain.auth.AuthRepository
import com.coursework.domain.bookDetails.BookDetailsRepository
import com.coursework.domain.books.BooksRepository
import com.coursework.domain.user.UserRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val authModule = module {
    single {
        TokenStore(
            dataStore = get(),
        )
    }

    factory {
        TokenRefresher(
            httpClient = get(named(NO_AUTH_CLIENT)),
            tokenStore = get(),
        )
    }

    factory {
        AuthTokenCache(
            httpClient = get(),
        )
    }

    factory {
        AuthApi(
            httpClient = get(named(NO_AUTH_CLIENT)),
        )
    }

    factory {
        UserApi(
            httpClient = get(),
        )
    }

    factoryOf(::UserTypeMapper)

    single<AuthRepository> {
        AuthRepositoryImpl(
            authApi = get(),
            tokenStore = get(),
            authTokenCache = get(),
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(
            userApi = get(),
            userTypeMapper = get(),
        )
    }
}

val dataModule = module {
    single<BooksRepository> {
        DummyBooksRepository(
            downloader = get(),
        )
    }

    single<BookDetailsRepository> {
        DummyBookDetailsRepository(
            namedItemResponseMapper = get(),
            reserveBookResponseMapper = get(),
        )
    }

    factoryOf(::NamedItemResponseMapper)
    factoryOf(::ReserveBookResponseMapper)
} + authModule + httpClientModule + tokenDataStoreModule + downloaderModule

expect val downloaderModule: Module
