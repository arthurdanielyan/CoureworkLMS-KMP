package com.coursework.featureBookDetails.detailsScreen.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coursework.corePresentation.commonUi.AsyncImage
import com.coursework.corePresentation.commonUi.FiveStarsRating
import com.coursework.corePresentation.commonUi.IconButton
import com.coursework.corePresentation.commonUi.LoadingStatePresenter
import com.coursework.corePresentation.commonUi.PrimaryButton
import com.coursework.corePresentation.commonUi.topBar.ContentWithTopBarHeader
import com.coursework.corePresentation.commonUi.topBar.TopBarBackButton
import com.coursework.corePresentation.commonUi.topBar.TopBarWithBackButton
import com.coursework.corePresentation.extensions.plus
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.featureBookDetails.BookDetailsDestination
import com.coursework.featureBookDetails.detailsScreen.BookDetailsUiCallbacks
import com.coursework.featureBookDetails.detailsScreen.BookDetailsViewModel
import com.coursework.featureBookDetails.detailsScreen.viewState.BookDetailsScreenViewState
import com.coursework.featureBookDetails.detailsScreen.viewState.BookDetailsViewState
import commonResources.author
import commonResources.categories
import commonResources.ic_favourites
import commonResources.ic_favourites_filled
import commonResources.language
import lms.featurebookdetails.generated.resources.authors
import lms.featurebookdetails.generated.resources.available_copies
import lms.featurebookdetails.generated.resources.book_cover_placeholder
import lms.featurebookdetails.generated.resources.download_pdf
import lms.featurebookdetails.generated.resources.edition
import lms.featurebookdetails.generated.resources.no_available_copies
import lms.featurebookdetails.generated.resources.published_by
import lms.featurebookdetails.generated.resources.published_in
import lms.featurebookdetails.generated.resources.reference_only_message
import lms.featurebookdetails.generated.resources.reserve
import lms.featurebookdetails.generated.resources.total_copies
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import commonResources.Res.drawable as CoreDrawables
import commonResources.Res.string as CoreStrings
import lms.featurebookdetails.generated.resources.Res.drawable as Drawables
import lms.featurebookdetails.generated.resources.Res.string as Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookDetailsScreen(
    destination: BookDetailsDestination
) {
    val viewModel = koinViewModel<BookDetailsViewModel>(
        parameters = {
            parametersOf(destination)
        }
    )

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val callbacks: BookDetailsUiCallbacks = viewModel

    BookDetailsScreen(
        state = state,
        callbacks = callbacks,
    )

    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()
    BookDetailsDialog(
        dialogState = dialogState,
        callbacks = callbacks
    )
}

@Composable
private fun BookDetailsScreen(
    state: BookDetailsScreenViewState,
    callbacks: BookDetailsUiCallbacks,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (state.dataLoadingState != DataLoadingState.Success) {
            TopBarWithBackButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                title = state.screenTitleWhileLoading,
                onBackClick = callbacks::onBackClick,
            )
        }
        LoadingStatePresenter(
            modifier = Modifier
                .fillMaxSize(),
            dataLoadingState = state.dataLoadingState,
            onRefresh = callbacks::onRefresh
        ) {
            state.bookDetails?.let {
                BookDetailsContent(
                    state = state,
                    callbacks = callbacks,
                )
            }
        }
    }
}

@Composable
private fun BookDetailsContent(
    state: BookDetailsScreenViewState,
    callbacks: BookDetailsUiCallbacks,
) {
    state.bookDetails ?: return
    ContentWithTopBarHeader(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        title = state.bookDetails.title,
        contentPadding = WindowInsets.navigationBars.asPaddingValues() +
                PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        onBackClick = callbacks::onBackClick,
        header = {
            BookDetailsHeader(
                modifier = Modifier
                    .fillMaxWidth(),
                onBackClick = callbacks::onBackClick,
                coverImageUrl = state.bookDetails.coverImageUrl,
                bookTitle = state.bookDetails.title
            )
        }
    ) {
        bookDetailsContent(
            state = state,
            onDownloadPdfClick = callbacks::onDownloadPdfClick,
            onReserveBookClick = callbacks::onReserveClick,
            onToReviewsClick = callbacks::onToReviewsClick,
        )
    }
}

private fun LazyListScope.bookDetailsContent(
    state: BookDetailsScreenViewState,
    onDownloadPdfClick: () -> Unit,
    onReserveBookClick: () -> Unit,
    onToReviewsClick: () -> Unit
) {
    val bookDetails = state.bookDetails ?: return
    if (bookDetails.hasPdfVersion) {
        item("action_buttons") {
            ActionButtons(
                onDownloadPdfClick = onDownloadPdfClick,
                showReserveButton = bookDetails.showBookButton,
                onReserveBookClick = onReserveBookClick
            )
        }
    }
    detailsPart(
        bookDetails = bookDetails
    )
    ratingPart(
        ratingDistribution = state.ratingDistribution,
        topReviews = state.topReviews,
        onToAllReviewsClick = onToReviewsClick
    )
}

@Composable
private fun ActionButtons(
    onDownloadPdfClick: () -> Unit,
    showReserveButton: Boolean,
    onReserveBookClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PrimaryButton(
            text = stringResource(Strings.download_pdf),
            onClick = onDownloadPdfClick
        )
        if (showReserveButton) {
            PrimaryButton(
                text = stringResource(Strings.reserve),
                onClick = onReserveBookClick,
            )
        }
        Spacer(Modifier.weight(1f))
        var isFavourite by rememberSaveable {
            mutableStateOf(false)
        }
        AnimatedContent(
            targetState = isFavourite,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            }
        ) {
            IconButton(
                imageVector = vectorResource(
                    if (it) {
                        CoreDrawables.ic_favourites_filled
                    } else {
                        CoreDrawables.ic_favourites
                    }
                ),
                iconSize = 28.dp,
                contentDescription = null,
                onClick = {
                    isFavourite = !isFavourite
                },
                tint = Color.Unspecified // TODO: add favourite color
            )
        }
    }
}

private fun LazyListScope.detailsPart(
    bookDetails: BookDetailsViewState
) {
    if (bookDetails.isReferenceOnly) {
        item("is_ref_only") {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = stringResource(Strings.reference_only_message),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
    if (bookDetails.subtitle?.isNotBlank() == true) {
        item("subtitle") {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = bookDetails.subtitle,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
    if (bookDetails.rating != null) {
        item("rating") {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = bookDetails.rating.toString(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineLarge,
                )
                FiveStarsRating(
                    rating = bookDetails.rating,
                )
            }
        }
    }
    item("description") {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = bookDetails.description,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
    if (bookDetails.authors.isNotEmpty()) {
        item("authors") {
            BookDetailBlock(
                title = stringResource(
                    if (bookDetails.authors.size > 1) Strings.authors
                    else CoreStrings.author
                ),
                detail = bookDetails.authors.joinToString(),
            )
        }
    }
    if (bookDetails.publisher?.isNotBlank() == true) {
        item("published_by") {
            BookDetailBlock(
                title = stringResource(Strings.published_by),
                detail = bookDetails.publisher,
            )
        }
    }
    if (bookDetails.publicationYear != null) {
        item("publication_year") {
            BookDetailBlock(
                title = stringResource(Strings.published_in),
                detail = bookDetails.publicationYear.toString(),
            )
        }
    }
    if (bookDetails.edition?.isNotBlank() == true) {
        item("edition") {
            BookDetailBlock(
                title = stringResource(Strings.edition),
                detail = bookDetails.edition,
            )
        }
    }
    if (bookDetails.categories.isNotEmpty()) {
        item("categories") {
            BookDetailBlock(
                title = stringResource(CoreStrings.categories),
                detail = bookDetails.categories.joinToString(),
            )
        }
    }
    item("total_copies") {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = "${stringResource(Strings.total_copies)} ${bookDetails.totalCopies}",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )
    }
    item("available_copies") {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = if (bookDetails.copiesAvailable > 0) {
                "${stringResource(Strings.available_copies)} ${bookDetails.totalCopies}"
            } else {
                stringResource(Strings.no_available_copies)
            },
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )
    }
    item("language") {
        BookDetailBlock(
            title = stringResource(CoreStrings.language),
            detail = bookDetails.language,
        )
    }
}

@Composable
private fun BookDetailBlock(
    title: String,
    detail: String,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement
            .spacedBy(8.dp)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = detail,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun BookDetailsHeader(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    coverImageUrl: String?,
    bookTitle: String,
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val headerHeight = remember(density, windowInfo) {
        density.run {
            (windowInfo.containerSize.height * 0.3f).toDp()
        }
    }
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        AsyncImage(
            model = coverImageUrl,
            placeholder = painterResource(Drawables.book_cover_placeholder),
            error = painterResource(Drawables.book_cover_placeholder),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(headerHeight),
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.surface)
                    )
                )
        )
        Text(
            text = bookTitle,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        )
        TopBarBackButton(
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
                .align(Alignment.TopStart),
            onClick = onBackClick,
        )
    }
}
