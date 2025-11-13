package com.coursework.featureBookDetails.common.viewState

import androidx.compose.runtime.Immutable

@Immutable
data class BookReviewViewState(
    val id: Long = -1,
    val username: String = "",
    val rating: Int = 0,
    val review: String = "",
)