package com.coursework.featureBookDetails.common.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.coursework.featureBookDetails.common.viewState.RatingDistributionBlockViewState
import lms.featurebookdetails.generated.resources.rating_reviews
import org.jetbrains.compose.resources.stringResource
import lms.featurebookdetails.generated.resources.Res.string as Strings

@Composable
internal fun RatingDistributionBlock(
    ratingDistribution: RatingDistributionBlockViewState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Strings.rating_reviews),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )
        RatingLine(
            percentage = ratingDistribution.ratingDistribution.oneStar,
            rating = 1,
            modifier = Modifier.fillMaxWidth(),
        )
        RatingLine(
            percentage = ratingDistribution.ratingDistribution.twoStars,
            rating = 2,
            modifier = Modifier.fillMaxWidth(),
        )
        RatingLine(
            percentage = ratingDistribution.ratingDistribution.threeStars,
            rating = 3,
            modifier = Modifier.fillMaxWidth(),
        )
        RatingLine(
            percentage = ratingDistribution.ratingDistribution.fourStars,
            rating = 4,
            modifier = Modifier.fillMaxWidth(),
        )
        RatingLine(
            percentage = ratingDistribution.ratingDistribution.fiveStars,
            rating = 5,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun RatingLine(
    percentage: Float,
    rating: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val lineBackground = MaterialTheme.colorScheme.secondaryContainer
        val lineColor = MaterialTheme.colorScheme.primary
        Canvas(
            modifier = Modifier
                .fillMaxWidth(0.7f)
        ) {
            drawLine(
                color = lineBackground,
                start = Offset.Zero,
                strokeWidth = 4.dp.toPx(),
                end = Offset(this.size.width, 0f),
                cap = StrokeCap.Round
            )
            drawLine(
                color = lineColor,
                start = Offset.Zero,
                strokeWidth = 4.dp.toPx(),
                end = Offset(this.size.width*percentage, 0f),
                cap = StrokeCap.Round
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = rating.toString(),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}
