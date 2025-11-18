package com.coursework.corePresentation.viewState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems

fun <T : Any> emptyPagingData(): PagingData<T> =
    PagingData.empty(
        sourceLoadStates = LoadStates(
            refresh = LoadState.Loading,
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            append = LoadState.Loading,
        )
    )

fun initialLoadingState(pagingLoadState: CombinedLoadStates): DataLoadingState {
    return when (pagingLoadState.refresh) {
        is LoadState.Loading -> DataLoadingState.Loading
        is LoadState.Error -> (pagingLoadState.refresh as LoadState.Error).error.toDataLoadingState()
        else -> DataLoadingState.Success
    }
}

@Composable
fun <T : Any> LazyPagingItems<T>.initialDataLoadingState(): State<DataLoadingState> {
    return remember {
        derivedStateOf {
            initialLoadingState(loadState)
        }
    }
}
