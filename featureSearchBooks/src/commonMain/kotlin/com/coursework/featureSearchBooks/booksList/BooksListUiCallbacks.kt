package com.coursework.featureSearchBooks.booksList

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.shared.books.BookViewState
import com.coursework.domain.books.model.SearchFilters

@Immutable
internal interface BooksListUiCallbacks {

    fun onGetFilterResult(filters: SearchFilters)
    fun onSearchQueryType(query: String)
    fun onBookClick(book: BookViewState)
    fun onSearchFiltersClick()
    fun onAddBookClick()
    fun onRefresh()
}