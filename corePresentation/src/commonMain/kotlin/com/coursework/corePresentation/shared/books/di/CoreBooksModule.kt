package com.coursework.corePresentation.shared.books.di

import com.coursework.corePresentation.shared.books.mapper.BookViewStateMapper
import com.coursework.corePresentation.shared.books.mapper.FilterViewStateMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val coreBooksModule = module {
    factoryOf(::BookViewStateMapper)
    factoryOf(::FilterViewStateMapper)
}