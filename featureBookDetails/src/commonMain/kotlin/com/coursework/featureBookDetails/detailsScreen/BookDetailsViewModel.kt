package com.coursework.featureBookDetails.detailsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.corePresentation.viewState.getErrorMessage
import com.coursework.corePresentation.viewState.toComposeList
import com.coursework.corePresentation.viewState.toDataLoadingState
import com.coursework.corePresentation.viewState.zipLoadingStates
import com.coursework.domain.bookDetails.model.BookDetails
import com.coursework.domain.bookDetails.usecases.GetBookDetailsUseCase
import com.coursework.domain.bookDetails.usecases.GetBookRatingDistribution
import com.coursework.domain.bookDetails.usecases.GetBookReviewsUseCase
import com.coursework.domain.bookDetails.usecases.GetMaxReservationDate
import com.coursework.domain.bookDetails.usecases.ReserveBookUseCase
import com.coursework.domain.books.model.PagingLimit
import com.coursework.domain.books.usecases.DownloadPdfUseCase
import com.coursework.featureBookDetails.common.BookDetailsDestination
import com.coursework.featureBookDetails.common.BookReviewsDestination
import com.coursework.featureBookDetails.common.viewState.BookReviewViewState
import com.coursework.featureBookDetails.common.viewState.RatingDistributionViewState
import com.coursework.featureBookDetails.detailsScreen.mapper.BookDetailsViewStateMapper
import com.coursework.featureBookDetails.detailsScreen.mapper.BookReviewViewStateMapper
import com.coursework.featureBookDetails.detailsScreen.mapper.RatingDistributionViewStateMapper
import com.coursework.featureBookDetails.detailsScreen.viewState.BookDetailsDialogState
import com.coursework.featureBookDetails.detailsScreen.viewState.BookDetailsScreenViewState
import com.coursework.featureBookDetails.detailsScreen.viewState.BookDetailsViewState
import com.coursework.utils.combine
import com.coursework.utils.mapList
import com.coursework.utils.stateInWhileSubscribed
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal class BookDetailsViewModel(
    private val destination: BookDetailsDestination,
    private val appRouter: AppRouter,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val downloadPdfUseCase: DownloadPdfUseCase,
    private val getMaxReservationDate: GetMaxReservationDate,
    private val reserveBookUseCase: ReserveBookUseCase,
    private val bookDetailsViewStateMapper: BookDetailsViewStateMapper,
    private val getBookRatingDistribution: GetBookRatingDistribution,
    private val ratingDistributionViewStateMapper: RatingDistributionViewStateMapper,
    private val getBookReviewsUseCase: GetBookReviewsUseCase,
    private val bookReviewViewStateMapper: BookReviewViewStateMapper,
) : ViewModel(), BookDetailsUiCallbacks {

    private companion object {
        const val TopReviewCount = 3
    }

    private var bookDetails: BookDetails? = null

    private val detailsLoadingState = MutableStateFlow(DataLoadingState.Loading)
    private val ratingDistributionLoadingState = MutableStateFlow(DataLoadingState.Loading)

    private val dataLoadingState = zipLoadingStates(
        detailsLoadingState,
        ratingDistributionLoadingState,
    )
    private val bookDetailsState = MutableStateFlow<BookDetailsViewState?>(null)
    private val isReserveButtonLoading = MutableStateFlow(false)
    private val ratingDistribution = MutableStateFlow(RatingDistributionViewState())
    private val topReviews = MutableStateFlow<List<BookReviewViewState>>(emptyList())
    private val showToAllReviewsButton = MutableStateFlow(false)

    private val reviewsExist = MutableStateFlow(false)

    private val refreshEvent = MutableSharedFlow<Unit>(
        replay = 1
    )

    private val _dialogState = MutableStateFlow<BookDetailsDialogState?>(null)
    val dialogState = _dialogState.asStateFlow()

    val uiState = combine(
        dataLoadingState,
        bookDetailsState,
        isReserveButtonLoading,
        ratingDistribution,
        topReviews,
        showToAllReviewsButton,
    ) { dataLoadingState,
        bookDetails,
        isReserveButtonLoading,
        ratingDistribution,
        topReviews,
        showToAllReviewsButton ->

        BookDetailsScreenViewState(
            screenTitleWhileLoading = destination.bookTitle,
            dataLoadingState = dataLoadingState,
            bookDetails = bookDetails?.copy(
                isReserveButtonLoading = isReserveButtonLoading,
            ),
            ratingDistribution = ratingDistribution,
            topReviews = topReviews.toComposeList(),
            showToAllReviewsButton = showToAllReviewsButton,
        )
    }.stateInWhileSubscribed(viewModelScope, BookDetailsScreenViewState())

    init {
        getBookDetails()
        getTopReviews()
        getRatingDistribution()
        onRefresh()
    }

    private fun getBookDetails() {
        viewModelScope.launch {
            refreshEvent
                .collectLatest {
                    detailsLoadingState.update { DataLoadingState.Loading }
                    getBookDetailsUseCase(destination.id)
                        .onSuccess { result ->
                            bookDetails = result
                            detailsLoadingState.update { DataLoadingState.Success }
                            bookDetailsState.update {
                                bookDetailsViewStateMapper.map(result)
                            }
                        }
                        .onFailure { throwable ->
                            detailsLoadingState.update { throwable.toDataLoadingState() }
                        }
                }
        }
    }

    private fun getRatingDistribution() {
        combine(
            refreshEvent,
            reviewsExist,
        ) { _, reviewsExist ->
            if (reviewsExist.not()) {
                ratingDistribution.update {
                    RatingDistributionViewState()
                }
                return@combine
            }
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
        }.launchIn(viewModelScope)
    }

    private fun getTopReviews() {
        viewModelScope.launch {
            refreshEvent.collectLatest {
                getBookReviewsUseCase(
                    GetBookReviewsUseCase.Params(
                        bookId = destination.id,
                        pagingLimit = PagingLimit(TopReviewCount)
                    )
                ).onSuccess { result ->
                    reviewsExist.update { result.reviews.isNotEmpty() }
                    showToAllReviewsButton.update { result.reviews.size > TopReviewCount }
                    topReviews.update {
                        bookReviewViewStateMapper.mapList(result.reviews)
                    }
                }
            }
        }
    }

    override fun onBackClick() {
        appRouter.pop()
    }

    override fun onDownloadPdfClick() {
        bookDetails?.let {
            downloadPdfUseCase(it)
        }
    }

    override fun onToReviewsClick() {
        appRouter.navigate(
            destination = BookReviewsDestination,
        )
    }

    @OptIn(ExperimentalTime::class)
    override fun onReserveClick() {
        viewModelScope.launch {
            getMaxReservationDate(
                bookId = destination.id
            ).onSuccess { maxDate ->
                _dialogState.update {
                    BookDetailsDialogState.ReturnDatePickerDialog(
                        maxDateMillis = maxDate.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
                    )
                }
            }.onFailure { throwable ->
                _dialogState.update {
                    BookDetailsDialogState.MessageDialog(
                        message = getErrorMessage(throwable)
                    )
                }
            }
        }
    }

    override fun onDismissDialog() {
        _dialogState.update { null }
    }

    @OptIn(ExperimentalTime::class)
    override fun onConfirmDate(dateMillis: Long) {
        viewModelScope.launch {
            val returnDate = Instant.fromEpochMilliseconds(dateMillis)
                .toLocalDateTime(TimeZone.UTC).date
            reserveBookUseCase(
                bookId = destination.id,
                returnDate = returnDate
            ).onSuccess { reservationResult ->
                _dialogState.update {
                    BookDetailsDialogState.MessageDialog(
                        message = StringValue.RawString(reservationResult.displayMessage),
                    )
                }
            }.onFailure { throwable ->
                _dialogState.update {
                    BookDetailsDialogState.MessageDialog(
                        message = getErrorMessage(throwable)
                    )
                }
            }
        }
    }

    override fun onRefresh() {
        refreshEvent.tryEmit(Unit)
    }
}