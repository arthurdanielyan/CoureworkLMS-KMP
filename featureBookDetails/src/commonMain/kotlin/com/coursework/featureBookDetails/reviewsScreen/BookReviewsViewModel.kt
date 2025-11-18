package com.coursework.featureBookDetails.reviewsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.coursework.corePresentation.commonUi.filters.FilterViewState
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.corePresentation.viewState.emptyPagingData
import com.coursework.corePresentation.viewState.toComposeList
import com.coursework.corePresentation.viewState.toDataLoadingState
import com.coursework.domain.bookDetails.usecases.GetBookRatingDistribution
import com.coursework.featureBookDetails.BookDetailsDestination
import com.coursework.featureBookDetails.common.viewState.RatingDistributionViewState
import com.coursework.featureBookDetails.detailsScreen.mapper.BookReviewViewStateMapper
import com.coursework.featureBookDetails.detailsScreen.mapper.RatingDistributionViewStateMapper
import com.coursework.featureBookDetails.reviewsScreen.viewState.BookReviewsViewState
import com.coursework.utils.stateInWhileSubscribed
import commonResources.ic_star
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lms.featurebookdetails.generated.resources.all
import lms.featurebookdetails.generated.resources.critical
import lms.featurebookdetails.generated.resources.positive
import commonResources.Res.drawable as CoreDrawables
import lms.featurebookdetails.generated.resources.Res.string as Strings

@OptIn(ExperimentalCoroutinesApi::class)
internal class BookReviewsViewModel(
    private val destination: BookDetailsDestination,
    private val appRouter: AppRouter,
    private val bookReviewsPagingSourceFactory: BookReviewsPagingSource.Factory,
    private val bookReviewViewStateMapper: BookReviewViewStateMapper,
    private val getBookRatingDistribution: GetBookRatingDistribution,
    private val ratingDistributionViewStateMapper: RatingDistributionViewStateMapper,
) : ViewModel(), BookReviewsUiCallbacks {

    private companion object {
        const val ReviewsPerPage = 20

        const val FILTER_ALL_ID = 1
        const val FILTER_POSITIVE_ID = 2
        const val FILTER_CRITICAL_ID = 3
        const val FILTER_FIVE_ID = 4
        const val FILTER_FOUR_ID = 5
        const val FILTER_THREE_ID = 6
        const val FILTER_TWO_ID = 7
        const val FILTER_ONE_ID = 8
    }

    private val refreshEvent = MutableSharedFlow<Unit>(
        replay = 1,
    )

    private val ratingDistribution = MutableStateFlow(RatingDistributionViewState())
    private val reviewFilters = MutableStateFlow(getReviewFilters())
    private val ratingDistributionLoadingState = MutableStateFlow(DataLoadingState.Loading)

    val reviewsPagingSource =
        combine(
            refreshEvent,
            reviewFilters
                .mapLatest { reviewFilters ->
                    reviewFilters.first { it.isSelected }.id
                },
            ::Pair
        ).flatMapLatest { (_, selectedFilterId) ->
            Pager(PagingConfig(pageSize = ReviewsPerPage)) {
                bookReviewsPagingSourceFactory(
                    bookId = destination.id,
                    filterId = selectedFilterId,
                )
            }.flow
        }.cachedIn(viewModelScope)
            .mapLatest { pagingData ->
                pagingData.map { review ->
                    bookReviewViewStateMapper(review)
                }
            }.stateInWhileSubscribed(
                viewModelScope,
                emptyPagingData()
            )

    val uiState = combine(
        ratingDistribution,
        reviewFilters,
        ratingDistributionLoadingState,
    ) { ratingDistribution, reviewFilters, dataLoadingState ->
        BookReviewsViewState(
            ratingDistribution = ratingDistribution,
            reviewFilters = reviewFilters.toComposeList(),
            ratingDistributionLoadingState = dataLoadingState,
        )
    }.stateInWhileSubscribed(viewModelScope, BookReviewsViewState())

    init {
        getRatingDistribution()
        onRefresh()
    }

    private fun getReviewFilters(): List<FilterViewState> {
        return buildList {
            add(
                FilterViewState(
                    id = FILTER_ALL_ID,
                    displayName = StringValue.StringResource(Strings.all),
                    isSelected = true,
                )
            )
            add(
                FilterViewState(
                    id = FILTER_POSITIVE_ID,
                    displayName = StringValue.StringResource(Strings.positive),
                    isSelected = false,
                )
            )
            add(
                FilterViewState(
                    id = FILTER_CRITICAL_ID,
                    displayName = StringValue.StringResource(Strings.critical),
                    isSelected = false,
                )
            )
            (5 downTo 1).forEach { star ->
                add(
                    FilterViewState(
                        id = star.getStarFilterId(),
                        displayName = StringValue.RawString(star.toString()),
                        isSelected = false,
                        leadingIconResId = CoreDrawables.ic_star,
                    )
                )
            }
        }
    }

    private fun Int.getStarFilterId(): Int {
        return when (this) {
            1 -> FILTER_ONE_ID
            2 -> FILTER_TWO_ID
            3 -> FILTER_THREE_ID
            4 -> FILTER_FOUR_ID
            else -> FILTER_FIVE_ID
        }
    }

    private fun getRatingDistribution() {
        viewModelScope.launch {
            refreshEvent.collectLatest {
                getBookRatingDistribution(destination.id)
                    .onSuccess { result ->
                        ratingDistribution.update {
                            ratingDistributionViewStateMapper.map(result)
                        }
                        ratingDistributionLoadingState.update { DataLoadingState.Success }
                    }
                    .onFailure { throwable ->
                        ratingDistributionLoadingState.update { throwable.toDataLoadingState() }
                    }
            }
        }
    }


    override fun onSelectReviewFilter(filter: FilterViewState) {
        reviewFilters.update {
            it.map { reviewFilter ->
                reviewFilter.copy(isSelected = reviewFilter.id == filter.id)
            }
        }
    }

    override fun onBackClick() {
        appRouter.pop()
    }

    override fun onRefresh() {
        refreshEvent.tryEmit(Unit)
    }
}
