package com.coursework.featureSearchBooks.shared.di

import com.coursework.featureSearchBooks.shared.SearchBooksSharedViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val searchBooksSharedModule = module {
    viewModelOf(::SearchBooksSharedViewModel)
}