package com.coursework.featureFavorites.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.coursework.corePresentation.commonUi.LoadingStatePresenter
import com.coursework.corePresentation.shared.books.BookViewState
import com.coursework.corePresentation.shared.books.ui.BookList
import com.coursework.corePresentation.viewState.initialDataLoadingState
import com.coursework.featureFavorites.presentation.FavouritesUiCallbacks
import com.coursework.featureFavorites.presentation.FavouritesViewModel
import com.coursework.featureFavorites.presentation.FavouritesViewState
import lms.featurefavorites.generated.resources.no_favourites
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import lms.featurefavorites.generated.resources.Res.string as Strings

@Composable
fun FavouritesScreen() {
    val viewModel = koinViewModel<FavouritesViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val booksPaging = viewModel.booksPagingSource.collectAsLazyPagingItems()
    val callbacks: FavouritesUiCallbacks = viewModel

    FavouritesScreen(
        state = state,
        booksPaging = booksPaging,
        callbacks = callbacks
    )
}

@Composable
private fun FavouritesScreen(
    state: FavouritesViewState,
    booksPaging: LazyPagingItems<BookViewState>,
    callbacks: FavouritesUiCallbacks
) {
    val initialLoadingState by booksPaging.initialDataLoadingState()
    LoadingStatePresenter(
        modifier = Modifier
            .fillMaxSize(),
        dataLoadingState = initialLoadingState,
        onRefresh = callbacks::onRefresh,
    ) {
        FavouritesContent(
            callbacks = callbacks,
            booksPaging = booksPaging,
        )
    }
}

@Composable
private fun FavouritesContent(
    callbacks: FavouritesUiCallbacks,
    booksPaging: LazyPagingItems<BookViewState>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        BookList(
            booksPagingItems = booksPaging,
            onBookClick = callbacks::onBookClick,
            emptyListPlaceholder = {
                NoFavorites()
            },
        )
    }

}

@Composable
private fun NoFavorites() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(Strings.no_favourites),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
