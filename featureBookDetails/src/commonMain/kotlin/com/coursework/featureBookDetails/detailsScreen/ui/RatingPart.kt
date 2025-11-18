package com.coursework.featureBookDetails.detailsScreen.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.coursework.corePresentation.commonUi.SpacerHeight
import com.coursework.corePresentation.commonUi.contrastClickable
import com.coursework.corePresentation.viewState.ComposeList
import com.coursework.featureBookDetails.common.ui.RatingDistributionBlock
import com.coursework.featureBookDetails.common.ui.ReviewItem
import com.coursework.featureBookDetails.common.viewState.BookReviewViewState
import com.coursework.featureBookDetails.common.viewState.RatingDistributionViewState
import lms.featurebookdetails.generated.resources.ic_next_screen
import lms.featurebookdetails.generated.resources.to_reviews
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import lms.featurebookdetails.generated.resources.Res.drawable as Drawables
import lms.featurebookdetails.generated.resources.Res.string as Strings

internal fun LazyListScope.ratingPart(
    ratingDistribution: RatingDistributionViewState,
    topReviews: ComposeList<BookReviewViewState>,
    onToAllReviewsClick: () -> Unit,
) {
    item("rating_distribution_block") {
        SpacerHeight(16.dp)
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        SpacerHeight(16.dp)
        RatingDistributionBlock(
            modifier = Modifier.padding(horizontal = 16.dp),
            ratingDistribution = ratingDistribution,
            showTitle = true,
        )
    }
    items(
        items = topReviews,
        key = { it.id }
    ) {
        ReviewItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            review = it,
        )
    }
    item {
        SpacerHeight(16.dp)
        ToReviewsButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onToAllReviewsClick,
        )
    }
}

@Composable
private fun ToReviewsButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .contrastClickable(onClick = onClick)
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Strings.to_reviews),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(Modifier.weight(1f))
        Icon(
            imageVector = vectorResource(Drawables.ic_next_screen),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}
