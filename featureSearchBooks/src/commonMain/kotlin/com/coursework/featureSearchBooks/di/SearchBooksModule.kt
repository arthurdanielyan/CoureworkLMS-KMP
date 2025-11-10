package com.coursework.featureSearchBooks.di

import com.coursework.featureSearchBooks.booksList.di.booksListModule
import com.coursework.featureSearchBooks.searchFilters.di.searchFiltersModule
import com.coursework.featureSearchBooks.shared.di.searchBooksSharedModule

val featureSearchBooksModule = booksListModule + searchFiltersModule + searchBooksSharedModule