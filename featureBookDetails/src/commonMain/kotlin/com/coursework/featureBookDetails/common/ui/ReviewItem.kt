package com.coursework.featureBookDetails.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coursework.corePresentation.commonUi.FiveStarsRating
import com.coursework.corePresentation.commonUi.SpacerHeight
import com.coursework.featureBookDetails.common.viewState.BookReviewViewState

@Composable
internal fun ReviewItem(
    review: BookReviewViewState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = review.username,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )
        SpacerHeight(2.dp)
        FiveStarsRating(
            rating = review.rating.toFloat(),
        )
        SpacerHeight(8.dp)
        Text(
            text = review.review,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}