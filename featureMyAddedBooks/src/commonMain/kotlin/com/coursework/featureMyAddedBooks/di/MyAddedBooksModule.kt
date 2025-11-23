package com.coursework.featureMyAddedBooks.di

import com.coursework.featureMyAddedBooks.presentation.MyAddedBooksPagingSourceFactory
import com.coursework.featureMyAddedBooks.presentation.MyAddedBooksViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureMyAddedBooksModule = module {
    viewModelOf(::MyAddedBooksViewModel)

    factoryOf(::MyAddedBooksPagingSourceFactory)
}