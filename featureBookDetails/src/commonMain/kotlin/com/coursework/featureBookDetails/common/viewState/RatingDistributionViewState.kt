package com.coursework.featureBookDetails.common.viewState

import androidx.compose.runtime.Immutable

@Immutable
data class RatingDistributionViewState(
    val oneStar: Float = 0f,
    val twoStars: Float = 0f,
    val threeStars: Float = 0f,
    val fourStars: Float = 0f,
    val fiveStars: Float = 0f
)