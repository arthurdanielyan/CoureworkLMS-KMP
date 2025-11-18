package com.coursework.featureBookDetails.reviewsScreen

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.commonUi.filters.FilterViewState

@Immutable
internal interface BookReviewsUiCallbacks {

    fun onSelectReviewFilter(filter: FilterViewState)
    fun onBackClick()
    fun onRefresh()
}