package com.coursework.featureSearchBooks.booksList.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.coursework.corePresentation.commonUi.IconButton
import com.coursework.corePresentation.commonUi.LoadingStatePresenter
import com.coursework.corePresentation.commonUi.TextField
import com.coursework.corePresentation.extensions.ComposeCollect
import com.coursework.corePresentation.shared.books.BookViewState
import com.coursework.corePresentation.shared.books.ui.BookList
import com.coursework.corePresentation.viewState.initialDataLoadingState
import com.coursework.featureSearchBooks.booksList.BooksListUiCallbacks
import com.coursework.featureSearchBooks.booksList.BooksListViewModel
import com.coursework.featureSearchBooks.booksList.viewState.BooksListViewState
import com.coursework.featureSearchBooks.shared.SearchBooksSharedViewModel
import commonResources.ic_close
import lms.featuresearchbooks.generated.resources.ic_filters
import lms.featuresearchbooks.generated.resources.no_books_found
import lms.featuresearchbooks.generated.resources.search_books
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import commonResources.Res.drawable as CoreDrawables
import lms.featuresearchbooks.generated.resources.Res.drawable as Drawables
import lms.featuresearchbooks.generated.resources.Res.string as Strings

@Composable
fun SearchBooksScreen(
    sharedViewModel: SearchBooksSharedViewModel,
) {
    val viewModel = koinViewModel<BooksListViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val books = viewModel.booksPagingSource.collectAsLazyPagingItems()

    ComposeCollect(sharedViewModel.searchFilters) {
        viewModel.onGetFilterResult(it)
    }

    SearchBooksScreen(
        state = state,
        booksPagingItems = books,
        callbacks = viewModel
    )
}

@Composable
private fun SearchBooksScreen(
    state: BooksListViewState,
    booksPagingItems: LazyPagingItems<BookViewState>,
    callbacks: BooksListUiCallbacks
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TextField(
            value = state.searchInput,
            onValueChange = callbacks::onSearchQueryType,
            label = stringResource(Strings.search_books),
            trailingIcon = {
                SearchFieldTrailingContent(
                    onClearQueryClick = { callbacks.onSearchQueryType("") },
                    onSearchFiltersClick = callbacks::onSearchFiltersClick
                )
            },
        )

        val initialDataLoadingState by booksPagingItems.initialDataLoadingState()
        LoadingStatePresenter(
            modifier = Modifier
                .fillMaxSize(),
            dataLoadingState = initialDataLoadingState,
            onRefresh = callbacks::onRefresh,
        ) {
            BookList(
                booksPagingItems = booksPagingItems,
                onBookClick = callbacks::onBookClick,
                emptyListPlaceholder = {
                    NoBooksFoundView()
                }
            )
        }
    }
}

@Composable
private fun SearchFieldTrailingContent(
    onClearQueryClick: () -> Unit,
    onSearchFiltersClick: () -> Unit,
) {
    Row(
        modifier = Modifier.padding(end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IconButton(
            imageVector = vectorResource(CoreDrawables.ic_close),
            onClick = onClearQueryClick,
            contentDescription = "Clear input"
        )
        IconButton(
            imageVector = vectorResource(Drawables.ic_filters),
            onClick = onSearchFiltersClick,
            contentDescription = "Search filters"
        )
    }
}

@Composable
private fun NoBooksFoundView() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Strings.no_books_found),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}
