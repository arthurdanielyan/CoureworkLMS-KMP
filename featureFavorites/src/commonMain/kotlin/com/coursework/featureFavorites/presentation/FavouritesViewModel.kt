package com.coursework.featureFavorites.presentation

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
import com.coursework.utils.stateInWhileSubscribed
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class FavouritesViewModel(
    private val appRouter: AppRouter,
    private val favouritesPagingSourceFactory: FavouritesPagingSourceFactory,
    private val bookViewStateMapper: BookViewStateMapper,
) : ViewModel(), FavouritesUiCallbacks {

    private companion object Companion {
        const val BooksPerPage = 20
    }

    private val refreshEvent = MutableSharedFlow<Unit>(
        replay = 1
    )

    private val dataLoadingState = MutableStateFlow(DataLoadingState.Loading)

    val uiState = dataLoadingState.mapLatest { dataLoadingState ->

        FavouritesViewState(
            dataLoadingState = dataLoadingState,
        )
    }.stateInWhileSubscribed(viewModelScope, FavouritesViewState())

    val booksPagingSource = refreshEvent
        .flatMapLatest {
            Pager(PagingConfig(pageSize = BooksPerPage)) {
                favouritesPagingSourceFactory()
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

    override fun onBookClick(book: BookViewState) {
        appRouter.navigate(
            destination = BookDetailsDestination(
                id = book.id,
                bookTitle = book.title,
            )
        )
    }
}