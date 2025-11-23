package com.coursework.featureMyAddedBooks.presentation

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.shared.books.BookViewState

@Immutable
internal interface MyAddedBooksUiCallbacks {

    fun onRefresh()
    fun onAddBookClick()
    fun onBookClick(book: BookViewState)
    fun onModifyBookClick(book: BookViewState)
    fun onRemoveBookClick(book: BookViewState)
}