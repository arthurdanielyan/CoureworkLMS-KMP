package com.coursework.featureBookDetails.reviewsScreen.viewState

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.commonUi.filters.FilterViewState
import com.coursework.corePresentation.viewState.ComposeList
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.emptyComposeList
import com.coursework.featureBookDetails.common.viewState.RatingDistributionViewState

@Immutable
data class BookReviewsViewState(
    val ratingDistribution: RatingDistributionViewState = RatingDistributionViewState(),
    val reviewFilters: ComposeList<FilterViewState> = emptyComposeList(),
    val ratingDistributionLoadingState: DataLoadingState = DataLoadingState.Loading,
)