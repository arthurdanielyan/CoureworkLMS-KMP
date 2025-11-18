package com.coursework.featureEditBook.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.LocalPlatformContext
import com.coursework.corePresentation.commonUi.LoadingStatePresenter
import com.coursework.corePresentation.commonUi.PrimaryButton
import com.coursework.corePresentation.commonUi.SecondaryButton
import com.coursework.corePresentation.commonUi.SpacerWidth
import com.coursework.corePresentation.commonUi.TextField
import com.coursework.corePresentation.commonUi.clickableWithoutIndication
import com.coursework.corePresentation.commonUi.filters.FilterSection
import com.coursework.corePresentation.commonUi.topBar.TopBarWithBackButton
import com.coursework.corePresentation.extensions.ObserveEffects
import com.coursework.corePresentation.extensions.isKeyboardVisible
import com.coursework.corePresentation.showError
import com.coursework.corePresentation.viewState.ActionWithString
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.featureEditBook.EditBookDestination
import com.coursework.featureEditBook.presentation.EditBookUiCallbacks
import com.coursework.featureEditBook.presentation.EditBookUiEffect
import com.coursework.featureEditBook.presentation.EditBookViewModel
import com.coursework.featureEditBook.presentation.viewState.EditBookViewState
import com.coursework.featureEditBook.ui.components.CoverImagePicker
import com.coursework.featureEditBook.ui.components.PdfPicker
import commonResources.cancel
import commonResources.categories
import commonResources.language
import commonResources.publication_year
import commonResources.reference_only
import lms.featureeditbook.generated.resources.authors_label
import lms.featureeditbook.generated.resources.authors_placeholder
import lms.featureeditbook.generated.resources.edition_label
import lms.featureeditbook.generated.resources.publish
import lms.featureeditbook.generated.resources.publisher_label
import lms.featureeditbook.generated.resources.required_fields_annotation
import lms.featureeditbook.generated.resources.subtitle_label
import lms.featureeditbook.generated.resources.title_label
import lms.featureeditbook.generated.resources.title_placeholder
import lms.featureeditbook.generated.resources.total_copies_label
import lms.featureeditbook.generated.resources.total_copies_placeholder
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import commonResources.Res.string as CoreStrings
import lms.featureeditbook.generated.resources.Res.string as Strings

@Composable
fun EditBookScreen(
    destination: EditBookDestination
) {
    val viewModel = koinViewModel<EditBookViewModel>(
        parameters = { parametersOf(destination) }
    )
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val callbacks: EditBookUiCallbacks = viewModel


    val context = LocalPlatformContext.current

    var message by remember {
        mutableStateOf<StringValue?>(null)
    }
    ActionWithString(message) {
        showError(it, context)
    }

    ObserveEffects(viewModel.uiEffect) { effect ->
        when (effect) {
            is EditBookUiEffect.ShowMessage -> {
                message = effect.message
            }
        }
    }

    EditBookScreen(
        state = state,
        callbacks = callbacks
    )
}

@Composable
private fun EditBookScreen(
    state: EditBookViewState,
    callbacks: EditBookUiCallbacks
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        TopBarWithBackButton(
            modifier = Modifier.fillMaxWidth(),
            title = state.topBarTitle.get(),
            onBackClick = callbacks::onBackClick
        )
        LoadingStatePresenter(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            dataLoadingState = state.dataLoadingState,
            onRefresh = {}
        ) {
            EditBookScreenContent(
                state = state,
                callbacks = callbacks,
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun EditBookScreenContent(
    state: EditBookViewState,
    callbacks: EditBookUiCallbacks
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val isKeyboardVisible by isKeyboardVisible()

    BackHandler {
        if (isKeyboardVisible) {
            keyboardController?.hide()
        } else {
            focusManager.clearFocus(force = true)
            callbacks.onBackClick()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .imePadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(Strings.required_fields_annotation),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary,
        )
        TextField(
            value = state.details.titleInput,
            onValueChange = callbacks::onTitleType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.title_label),
            placeholder = stringResource(Strings.title_placeholder),
            isError = state.details.titleInput.isBlank(),
            errorMessageEnabled = true,
        )
        TextField(
            value = state.details.subtitleInput,
            onValueChange = callbacks::onSubtitleType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.subtitle_label),
        )
        TextField(
            value = state.details.authorsInput,
            onValueChange = callbacks::onAuthorsType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.authors_label),
            placeholder = stringResource(Strings.authors_placeholder),
            isError = state.details.authorsInput.isBlank(),
            errorMessageEnabled = true,
        )
        TextField(
            value = state.details.publisherInput,
            onValueChange = callbacks::onPublisherType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.publisher_label),
        )
        TextField(
            value = state.details.publicationYearInput,
            onValueChange = callbacks::onPublicationYearType,
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(CoreStrings.publication_year),
            isError = state.details.isPublicationYearInputValid.not(),
            errorMessageEnabled = true,
        )
        TextField(
            value = state.details.editionInput,
            onValueChange = callbacks::onEditionType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.edition_label),
        )
        TextField(
            value = state.details.totalCopiesInput,
            onValueChange = callbacks::onTotalCopiesType,
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.total_copies_label),
            placeholder = stringResource(Strings.total_copies_placeholder),
            isError = state.details.isTotalCopiesInputValid.not(),
            errorMessageEnabled = true,
        )
        FilterSection(
            modifier = Modifier
                .padding(vertical = 8.dp),
            title = stringResource(CoreStrings.categories),
            filters = state.details.categories,
            onFilterSelect = callbacks::onCategorySelected,
        )
        FilterSection(
            modifier = Modifier
                .padding(vertical = 8.dp),
            title = stringResource(CoreStrings.language),
            filters = state.details.languages,
            onFilterSelect = callbacks::onLanguageSelected,
        )
        PdfPicker(
            bookPdf = state.details.bookPdf,
            onPickFileClick = callbacks::onPickPdfClick,
            onOpenFileClick = callbacks::onOpenPdf,
            onRemoveClick = callbacks::onRemovePdf,
        )
        CoverImagePicker(
            coverImage = state.details.coverImage,
            onPickImageClick = callbacks::onPickCoverImageClick,
            onRemoveClick = callbacks::onRemoveImageClick,
        )
        Row(
            modifier = Modifier
                .clickableWithoutIndication(
                    onClick = {
                        callbacks.onReferenceCheckedStateChanged(state.details.isReferenceOnlyChecked.not())
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = state.details.isReferenceOnlyChecked,
                onCheckedChange = callbacks::onReferenceCheckedStateChanged,
            )
            Text(
                text = stringResource(CoreStrings.reference_only),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            SecondaryButton(
                modifier = Modifier.weight(1f),
                text = stringResource(CoreStrings.cancel),
                onClick = callbacks::onCancelClick
            )
            SpacerWidth(16.dp)
            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = stringResource(Strings.publish),
                onClick = callbacks::onPublishClick
            )
        }
    }
}
