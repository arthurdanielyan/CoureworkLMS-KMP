package com.coursework.featureEditBook.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.commonUi.filters.FilterViewState
import com.coursework.corePresentation.externalActivityLauncher.ExternalActivityLauncher
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.shared.books.mapper.FilterViewStateMapper
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.corePresentation.viewState.toComposeList
import com.coursework.corePresentation.viewState.toDataLoadingState
import com.coursework.domain.bookDetails.usecases.GetBookDetailsUseCase
import com.coursework.domain.bookDetails.usecases.GetCategories
import com.coursework.domain.bookDetails.usecases.GetLanguages
import com.coursework.featureEditBook.EditBookDestination
import com.coursework.featureEditBook.presentation.mapper.EditBookViewStateMapper
import com.coursework.featureEditBook.presentation.viewState.BookPdfViewState
import com.coursework.featureEditBook.presentation.viewState.CoverImageViewState
import com.coursework.featureEditBook.presentation.viewState.EditBookDetailsViewState
import com.coursework.featureEditBook.presentation.viewState.EditBookViewState
import com.coursework.utils.mapList
import com.coursework.utils.stateInWhileSubscribed
import commonResources.unknown_error_occurred
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lms.featureeditbook.generated.resources.add_book
import lms.featureeditbook.generated.resources.no_pdf_viewer
import commonResources.Res.string as CoreStrings
import lms.featureeditbook.generated.resources.Res.string as Strings

internal class EditBookViewModel(
    destination: EditBookDestination,
    private val appRouter: AppRouter,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val getCategories: GetCategories,
    private val getLanguages: GetLanguages,
    private val filterViewStateMapper: FilterViewStateMapper,
    private val editBookViewStateMapper: EditBookViewStateMapper,
    private val externalActivityLauncher: ExternalActivityLauncher,
) : ViewModel(), EditBookUiCallbacks {

    private companion object {
        const val PDF_FILE_EXTENSION = "pdf"
    }

    private val _uiEffect = Channel<EditBookUiEffect>(
        capacity = Channel.BUFFERED,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val uiEffect = _uiEffect.receiveAsFlow()

    private val details = MutableStateFlow(EditBookDetailsViewState())
    private val topBarTitle = MutableStateFlow<StringValue>(StringValue.RawString(destination.bookTitle.orEmpty()))
    private val dataLoadingState = MutableStateFlow(DataLoadingState.Loading)
    val uiState = combine(
        topBarTitle,
        details,
        dataLoadingState,
    ) { topBarTitle, details, dataLoadingState ->

        EditBookViewState(
            topBarTitle = topBarTitle,
            details = details,
            dataLoadingState = dataLoadingState,
        )
    }.stateInWhileSubscribed(viewModelScope, EditBookViewState())

    private var coverImageBytes: ByteArray? = null // will be used to send to backend
    private var pdfBytes: ByteArray? = null // will be used to send to backend
    private var pdfFilePath: String? = null

    init {
        if (destination.isNewBook) {
            dataLoadingState.update { DataLoadingState.Success }
            topBarTitle.update { StringValue.StringResource(Strings.add_book) }
            setupCategories()
        } else {
            destination.bookId?.let(::getBookDetails)
        }
    }

    private fun getBookDetails(bookId: Long) {
        viewModelScope.launch {
            dataLoadingState.update { DataLoadingState.Loading }
            delay(1000) // simulate loading
            val categories = getCategories()
                .onFailure {
                    dataLoadingState.update { DataLoadingState.Error }
                    return@launch
                }.getOrDefault(emptyList())
            getBookDetailsUseCase(bookId)
                .onSuccess { result ->
                    details.update {
                        editBookViewStateMapper.map(
                            from = result,
                            params = EditBookViewStateMapper.Params(
                                allCategories = filterViewStateMapper.mapList(
                                    list = categories,
                                    params = { FilterViewStateMapper.Params(isSelected = false) }
                                )
                            )
                        )
                    }
                    topBarTitle.update { StringValue.RawString(result.title) }
                    dataLoadingState.update { DataLoadingState.Success }
                }
                .onFailure {
                    dataLoadingState.update { DataLoadingState.Error }
                }
        }
    }

    private fun setupCategories() {
        viewModelScope.launch {
            runCatching {
                getCategories()
                    .onSuccess { categories ->
                        details.update { oldState ->
                            oldState.copy(
                                categories = filterViewStateMapper.mapList(
                                    list = categories,
                                    params = { category ->
                                        FilterViewStateMapper.Params(
                                            isSelected = details.value.categories
                                                .firstOrNull { it.id == category.id } != null
                                        )
                                    }
                                ).toComposeList()
                            )
                        }
                    }.getOrThrow()
                getLanguages()
                    .onSuccess { languages ->
                        details.update { oldState ->
                            oldState.copy(
                                languages = filterViewStateMapper.mapList(
                                    list = languages,
                                    params = { language ->
                                        FilterViewStateMapper.Params(
                                            isSelected = details.value.languages
                                                .firstOrNull { it.id == language.id } != null
                                        )
                                    }
                                ).toComposeList()
                            )
                        }
                    }.getOrThrow()
            }.onSuccess {
                dataLoadingState.update { DataLoadingState.Success }
            }.onFailure { throwable ->
                dataLoadingState.update { throwable.toDataLoadingState() }
            }
        }
    }

    override fun onBackClick() {
        appRouter.pop()
    }

    override fun onTitleType(newValue: String) {
        details.update {
            it.copy(titleInput = newValue)
        }
    }

    override fun onSubtitleType(newValue: String) {
        details.update {
            it.copy(subtitleInput = newValue)
        }
    }

    override fun onAuthorsType(newValue: String) {
        details.update {
            it.copy(authorsInput = newValue)
        }
    }

    override fun onPublisherType(newValue: String) {
        details.update {
            it.copy(publisherInput = newValue)
        }
    }

    override fun onPublicationYearType(newValue: String) {
        details.update {
            it.copy(publicationYearInput = newValue)
        }
    }

    override fun onEditionType(newValue: String) {
        details.update {
            it.copy(editionInput = newValue)
        }
    }

    override fun onCategorySelected(categoryFilter: FilterViewState) {
        details.update {
            it.copy(
                categories = it.categories.map { filter ->
                    if (filter.id == categoryFilter.id) {
                        filter.copy(isSelected = filter.isSelected.not())
                    } else {
                        filter
                    }
                }.toComposeList()
            )
        }
    }

    override fun onLanguageSelected(languageFilter: FilterViewState) {
        details.update {
            it.copy(
                languages = it.languages.map { filter ->
                    filter.copy(isSelected = filter.id == languageFilter.id)
                }.toComposeList()
            )
        }
    }

    override fun onTotalCopiesType(newValue: String) {
        details.update {
            it.copy(totalCopiesInput = newValue)
        }
    }

    override fun onPickCoverImageClick() {
        viewModelScope.launch {
            val coverImageFile = FileKit.openFilePicker(
                mode = FileKitMode.Single,
                type = FileKitType.Image
            ) ?: return@launch
            coverImageBytes = coverImageFile.readBytes()
            details.update {
                it.copy(
                    coverImage = CoverImageViewState(
                        url = null,
                        localUri = coverImageFile.path
                    )
                )
            }
        }
    }

    override fun onPickPdfClick() {
        viewModelScope.launch {
            val pdfFile = FileKit.openFilePicker(
                mode = FileKitMode.Single,
                type = FileKitType.File(setOf(PDF_FILE_EXTENSION))
            ) ?: return@launch
            pdfBytes = pdfFile.readBytes()
            pdfFilePath = pdfFile.path
            details.update {
                it.copy(
                    bookPdf = BookPdfViewState(
                        pdfUrl = null,
                        localFileSelected = true,
                        displayName = pdfFile.name,
                    )
                )
            }
        }
    }

    override fun onRemovePdf() {
        pdfBytes = null
        pdfFilePath = null
        details.update {
            it.copy(
                bookPdf = BookPdfViewState()
            )
        }
    }

    override fun onOpenPdf() {
        val pdfPath = pdfFilePath
        if (pdfPath != null) {
            when (externalActivityLauncher.openLocalPdf(pdfPath)) {
                ExternalActivityLauncher.ResultCodes.NoActivityFound -> {
                    _uiEffect.trySend(
                        EditBookUiEffect.ShowMessage(
                            StringValue.StringResource(CoreStrings.unknown_error_occurred)
                        )
                    )
                }

                ExternalActivityLauncher.ResultCodes.NoActivityFound -> {
                    _uiEffect.trySend(
                        EditBookUiEffect.ShowMessage(
                            StringValue.StringResource(Strings.no_pdf_viewer)
                        )
                    )
                }
            }
        } else {
            uiState.value.details.bookPdf.pdfUrl?.let {
                externalActivityLauncher.openUrl(it)
            }
        }
    }

    override fun onRemoveImageClick() {
        coverImageBytes = null
        details.update {
            it.copy(
                coverImage = CoverImageViewState()
            )
        }
    }

    override fun onReferenceCheckedStateChanged(checked: Boolean) {
        details.update {
            it.copy(
                isReferenceOnlyChecked = checked
            )
        }
    }

    override fun onCancelClick() {
        appRouter.pop()
    }

    override fun onPublishClick() {
        // TODO: Not yet implemented
    }
}