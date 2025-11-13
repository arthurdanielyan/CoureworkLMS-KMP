package com.coursework.featureBookDetails.reviewsScreen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.coursework.featureBookDetails.common.ui.ReviewItem
import com.coursework.featureBookDetails.common.viewState.BookReviewViewState
import lms.featurebookdetails.generated.resources.no_reviews
import lms.featurebookdetails.generated.resources.rating_reviews
import org.jetbrains.compose.resources.stringResource
import lms.featurebookdetails.generated.resources.Res.string as Strings

internal fun LazyListScope.reviewList(
    reviews: LazyPagingItems<BookReviewViewState>,
    modifier: Modifier = Modifier,
) {
    val isEmpty = reviews.loadState.refresh is LoadState.NotLoading &&
            reviews.itemSnapshotList.isEmpty()


    if (isEmpty) {
        item("no_reviews_placeholder") {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(Strings.no_reviews),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    } else {
        item {
            Text(
                text = stringResource(Strings.rating_reviews),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        items(reviews.itemCount) { index ->
            val review = reviews[index] ?: return@items
            ReviewItem(
                modifier = Modifier.padding(horizontal = 16.dp),
                review = review,
            )
        }
    }
}


