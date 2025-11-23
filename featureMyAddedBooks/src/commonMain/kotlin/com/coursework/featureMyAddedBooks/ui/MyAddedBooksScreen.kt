package com.coursework.featureMyAddedBooks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.coursework.corePresentation.commonUi.ContentWithFab
import com.coursework.corePresentation.commonUi.IconButton
import com.coursework.corePresentation.commonUi.LoadingStatePresenter
import com.coursework.corePresentation.shared.books.BookViewState
import com.coursework.corePresentation.shared.books.ui.BookList
import com.coursework.corePresentation.shared.books.ui.BookListActions
import com.coursework.corePresentation.viewState.initialDataLoadingState
import com.coursework.featureMyAddedBooks.presentation.MyAddedBooksUiCallbacks
import com.coursework.featureMyAddedBooks.presentation.MyAddedBooksViewModel
import com.coursework.featureMyAddedBooks.presentation.MyAddedBooksViewState
import commonResources.ic_add
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import commonResources.Res.drawable as CoreDrawables

@Composable
fun MyAddedBooksScreen() {
    val viewModel = koinViewModel<MyAddedBooksViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val booksPaging = viewModel.booksPagingSource.collectAsLazyPagingItems()
    val callbacks: MyAddedBooksUiCallbacks = viewModel

    MyAddedBooksScreen(
        state = state,
        booksPaging = booksPaging,
        callbacks = callbacks
    )
}

@Composable
private fun MyAddedBooksScreen(
    state: MyAddedBooksViewState,
    booksPaging: LazyPagingItems<BookViewState>,
    callbacks: MyAddedBooksUiCallbacks
) {
    val initialLoadingState by booksPaging.initialDataLoadingState()
    LoadingStatePresenter(
        modifier = Modifier
            .fillMaxSize(),
        dataLoadingState = initialLoadingState,
        onRefresh = callbacks::onRefresh,
    ) {
        MyAddedBooksContent(
            callbacks = callbacks,
            booksPaging = booksPaging,
        )
    }
}

@Composable
private fun MyAddedBooksContent(
    callbacks: MyAddedBooksUiCallbacks,
    booksPaging: LazyPagingItems<BookViewState>,
) {
    ContentWithFab(
        floatingActionButton = {
            IconButton(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape
                    ),
                imageVector = vectorResource(CoreDrawables.ic_add),
                contentDescription = "Add a book",
                iconSize = 36.dp,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = callbacks::onAddBookClick,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            BookList(
                booksPagingItems = booksPaging,
                onBookClick = callbacks::onBookClick,
                bookListActions = BookListActions(
                    onModifyClick = callbacks::onModifyBookClick,
                    onRemoveClick = callbacks::onRemoveBookClick,
                ),
                emptyListPlaceholder = {

                },
            )
        }
    }
}

@Composable
private fun NoAddedBooks() {

}
