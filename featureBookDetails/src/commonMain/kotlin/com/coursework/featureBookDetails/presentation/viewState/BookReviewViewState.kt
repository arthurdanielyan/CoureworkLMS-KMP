package com.coursework.featureBookDetails.presentation.viewState

import androidx.compose.runtime.Immutable

@Immutable
data class BookReviewViewState(
    val username: String = "",
    val rating: Int = 0,
    val review: String = "",
)