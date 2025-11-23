package com.coursework.featureMyAddedBooks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.shared.books.BookViewState
import com.coursework.corePresentation.shared.books.mapper.BookViewStateMapper
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.emptyPagingData
import com.coursework.featureBookDetails.BookDetailsDestination
import com.coursework.featureEditBook.EditBookDestination
import com.coursework.utils.stateInWhileSubscribed
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class MyAddedBooksViewModel(
    private val appRouter: AppRouter,
    private val myAddedBooksPagingSourceFactory: MyAddedBooksPagingSourceFactory,
    private val bookViewStateMapper: BookViewStateMapper,
) : ViewModel(), MyAddedBooksUiCallbacks {

    private companion object {
        const val BooksPerPage = 20
    }

    private val refreshEvent = MutableSharedFlow<Unit>(
        replay = 1
    )

    private val dataLoadingState = MutableStateFlow(DataLoadingState.Loading)

    val uiState = dataLoadingState.mapLatest { dataLoadingState ->

        MyAddedBooksViewState(
            dataLoadingState = dataLoadingState,
        )
    }.stateInWhileSubscribed(viewModelScope, MyAddedBooksViewState())

    val booksPagingSource = refreshEvent
        .flatMapLatest {
            Pager(PagingConfig(pageSize = BooksPerPage)) {
                myAddedBooksPagingSourceFactory()
            }.flow
        }.cachedIn(viewModelScope)
        .mapLatest { pagingData ->
            pagingData.map { book ->
                bookViewStateMapper(book)
            }
        }.stateInWhileSubscribed(
            viewModelScope,
            emptyPagingData()
        )

    init {
        onRefresh()
    }

    override fun onRefresh() {
        refreshEvent.tryEmit(Unit)
    }

    override fun onAddBookClick() {
        appRouter.navigate(
            EditBookDestination(
                isNewBook = true,
            )
        )
    }

    override fun onBookClick(book: BookViewState) {
        appRouter.navigate(
            destination = BookDetailsDestination(
                id = book.id,
                bookTitle = book.title,
            )
        )
    }

    override fun onModifyBookClick(book: BookViewState) {
        appRouter.navigate(
            destination = EditBookDestination(
                bookId = book.id,
                bookTitle = book.title,
                isNewBook = false,
            )
        )
    }

    override fun onRemoveBookClick(book: BookViewState) {
        // TODO : Not yet implemented
    }
}