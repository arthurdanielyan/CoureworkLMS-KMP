package com.coursework.data.di

import com.coursework.data.bookDetails.DummyBookDetailsRepository
import com.coursework.data.bookDetails.mapper.NamedItemResponseMapper
import com.coursework.data.bookDetails.mapper.ReserveBookResponseMapper
import com.coursework.data.books.DummyBooksRepository
import com.coursework.domain.bookDetails.BookDetailsRepository
import com.coursework.domain.books.BooksRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

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
} + downloaderModule

expect val downloaderModule: Module