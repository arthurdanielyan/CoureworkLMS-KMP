package com.coursework.featureFavorites.presentation

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.DataLoadingState

@Immutable
data class FavouritesViewState(
    val dataLoadingState: DataLoadingState = DataLoadingState.Loading,
)
