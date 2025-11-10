package com.coursework.featureBookDetails.presentation.viewState

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.ComposeList
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.StringValue
import com.coursework.domain.bookDetails.model.NamedItem

@Immutable
internal data class BookDetailsScreenViewState(
    val dataLoadingState: DataLoadingState = DataLoadingState.Loading,
    val bookDetails: BookDetailsViewState? = null,
)

@Immutable
internal data class BookDetailsViewState(
    val id: Long,
    val title: String,
    val subtitle: String?,
    val authors: ComposeList<String>,
    val publisher: String?,
    val publicationYear: Int?,
    val edition: String?,
    val categories: ComposeList<NamedItem>,
    val hasPdfVersion: Boolean,
    val pdfUrl: String?,
    val coverImageUrl: String?,
    val totalCopies: Int,
    val copiesAvailable: Int,
    val language: String,
    val isReferenceOnly: Boolean,
    val isReserveButtonLoading: Boolean = false,
) {
    val showBookButton = copiesAvailable > 0 && isReferenceOnly.not()
}

@Immutable
sealed interface BookDetailsDialogState {

    data class ReturnDatePickerDialog(
        val maxDateMillis: Long = Long.MAX_VALUE,
        val inConfirmButtonLoading: Boolean = false
    ) : BookDetailsDialogState

    data class MessageDialog(
        val message: StringValue
    ) : BookDetailsDialogState
}
