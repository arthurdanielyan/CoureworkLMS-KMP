package com.coursework.featureFavorites.presentation

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.shared.books.BookViewState

@Immutable
internal interface FavouritesUiCallbacks {

    fun onRefresh()
    fun onBookClick(book: BookViewState)
}