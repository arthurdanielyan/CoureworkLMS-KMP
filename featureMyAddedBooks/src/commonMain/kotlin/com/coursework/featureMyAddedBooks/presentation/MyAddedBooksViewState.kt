package com.coursework.featureMyAddedBooks.presentation

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.DataLoadingState

@Immutable
data class MyAddedBooksViewState(
    val dataLoadingState: DataLoadingState = DataLoadingState.Loading,
)
