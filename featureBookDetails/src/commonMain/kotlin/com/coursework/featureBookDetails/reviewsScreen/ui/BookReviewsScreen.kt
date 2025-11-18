package com.coursework.featureBookDetails.reviewsScreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.coursework.corePresentation.commonUi.LoadingStatePresenter
import com.coursework.corePresentation.commonUi.filters.FilterChipRow
import com.coursework.corePresentation.commonUi.pagingBottomContent
import com.coursework.corePresentation.commonUi.topBar.TopBarWithBackButton
import com.coursework.corePresentation.extensions.plus
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.initialDataLoadingState
import com.coursework.featureBookDetails.BookDetailsDestination
import com.coursework.featureBookDetails.common.ui.RatingDistributionBlock
import com.coursework.featureBookDetails.common.ui.ReviewItem
import com.coursework.featureBookDetails.common.viewState.BookReviewViewState
import com.coursework.featureBookDetails.reviewsScreen.BookReviewsUiCallbacks
import com.coursework.featureBookDetails.reviewsScreen.BookReviewsViewModel
import com.coursework.featureBookDetails.reviewsScreen.viewState.BookReviewsViewState
import lms.featurebookdetails.generated.resources.rating_reviews
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import lms.featurebookdetails.generated.resources.Res.string as Strings

@Composable
internal fun BookReviewsScreen(
    bookDetailsDestination: BookDetailsDestination
) {
    val viewModel = koinViewModel<BookReviewsViewModel>(
        parameters = {
            parametersOf(bookDetailsDestination)
        }
    )
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val reviewsPaging = viewModel.reviewsPagingSource.collectAsLazyPagingItems()
    val callbacks: BookReviewsUiCallbacks = viewModel

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBarWithBackButton(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Strings.rating_reviews),
            onBackClick = callbacks::onBackClick
        )
        LoadingStatePresenter(
            modifier = Modifier.fillMaxSize(),
            dataLoadingState = state.ratingDistributionLoadingState,
            onRefresh = callbacks::onRefresh
        ) {
            BookReviewsContent(
                state = state,
                reviewsPaging = reviewsPaging,
                callbacks = callbacks,
            )
        }
    }
}

@Composable
private fun BookReviewsContent(
    state: BookReviewsViewState,
    reviewsPaging: LazyPagingItems<BookReviewViewState>,
    callbacks: BookReviewsUiCallbacks,
) {
    val initialLoadingState by reviewsPaging.initialDataLoadingState()
    Column {
        LazyColumn(
            modifier = Modifier,
            contentPadding = WindowInsets.navigationBars.asPaddingValues() +
                    PaddingValues(
                        top = 16.dp,
                        bottom = 8.dp
                    ),
            verticalArrangement = Arrangement.spacedBy(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item("rating_distribution_block") {
                RatingDistributionBlock(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    ratingDistribution = state.ratingDistribution,
                    showTitle = false,
                )
            }
            if(initialLoadingState.isError.not()) {
                item("review_filters") {
                    FilterChipRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        filters = state.reviewFilters,
                        onFilterSelect = callbacks::onSelectReviewFilter,
                    )
                }
            }
            if (initialLoadingState == DataLoadingState.Success) {
                reviewList(
                    reviews = reviewsPaging
                )
                pagingBottomContent(
                    pagingItems = reviewsPaging
                )
            }
        }
        if (initialLoadingState != DataLoadingState.Success) {
            LoadingStatePresenter(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .weight(1f),
                dataLoadingState = initialLoadingState,
                onRefresh = {
                    reviewsPaging.retry()
                }
            ){}
        }
    }
}

private fun LazyListScope.reviewList(
    reviews: LazyPagingItems<BookReviewViewState>,
) {
    items(reviews.itemCount) { index ->
        val review = reviews[index] ?: return@items
        ReviewItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            review = review,
        )
    }
}
