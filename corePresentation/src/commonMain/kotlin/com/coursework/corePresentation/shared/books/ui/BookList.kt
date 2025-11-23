package com.coursework.corePresentation.shared.books.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.coursework.corePresentation.commonUi.pagingBottomContent
import com.coursework.corePresentation.shared.books.BookViewState

@Immutable
class BookListActions(
    val onModifyClick: (BookViewState) -> Unit,
    val onRemoveClick: (BookViewState) -> Unit,
)

@Composable
fun BookList(
    booksPagingItems: LazyPagingItems<BookViewState>,
    onBookClick: (BookViewState) -> Unit,
    bookListActions: BookListActions? = null,
    emptyListPlaceholder: @Composable () -> Unit = {},
) {
    val isEmpty by remember(booksPagingItems.loadState.refresh, booksPagingItems.itemSnapshotList) {
        mutableStateOf(
            booksPagingItems.loadState.refresh is LoadState.NotLoading &&
                    booksPagingItems.itemSnapshotList.isEmpty()
        )
    }
    if (isEmpty) {
        emptyListPlaceholder()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface
                ),
            contentPadding = PaddingValues(
                bottom = 16.dp,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(booksPagingItems.itemCount) { index ->
                val book = booksPagingItems[index] ?: return@items
                BookItem(
                    book = book,
                    onClick = {
                        onBookClick(book)
                    },
                    bookModifyActions = remember(book, bookListActions) {
                        bookListActions?.let { bookModifyActions ->
                            BookItemActions(
                                onModifyClick = {
                                    bookModifyActions.onModifyClick.invoke(book)
                                },
                                onRemoveClick = {
                                    bookModifyActions.onRemoveClick.invoke(book)
                                }
                            )
                        }
                    },
                )
            }

            pagingBottomContent(
                pagingItems = booksPagingItems
            )
        }
    }
}
