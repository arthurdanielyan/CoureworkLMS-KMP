package com.coursework.featureEditBook.presentation.mapper

import com.coursework.corePresentation.commonUi.filters.FilterViewState
import com.coursework.corePresentation.viewState.toComposeList
import com.coursework.domain.bookDetails.model.BookDetails
import com.coursework.featureEditBook.presentation.viewState.BookPdfViewState
import com.coursework.featureEditBook.presentation.viewState.CoverImageViewState
import com.coursework.featureEditBook.presentation.viewState.EditBookDetailsViewState
import com.coursework.utils.ParameterizedMapper

internal class EditBookViewStateMapper
    : ParameterizedMapper<BookDetails, EditBookViewStateMapper.Params, EditBookDetailsViewState> {

    data class Params(
        val allCategories: List<FilterViewState>
    )

    override fun map(from: BookDetails, params: Params): EditBookDetailsViewState {
        val bookCategories = from.categories.map { it.id }
        return EditBookDetailsViewState(
            titleInput = from.title,
            subtitleInput = from.subtitle.orEmpty(),
            authorsInput = from.authors.joinToString(),
            publisherInput = from.publisher.orEmpty(),
            publicationYearInput = from.publicationYear?.toString().orEmpty(),
            editionInput = from.edition.orEmpty(),
            categories = params.allCategories.map { category ->
                category.copy(
                    isSelected = bookCategories.contains(category.id)
                )
            }.toComposeList(),
            bookPdf = BookPdfViewState(
                pdfUrl = from.pdfUrl.toString(),
                displayName = from.pdfTitle.toString(),
            ),
            coverImage = CoverImageViewState(
                url = from.coverImageUrl.toString(),
            ),
            totalCopiesInput = from.totalCopies.toString(),
            isReferenceOnlyChecked = from.isReferenceOnly,
        )
    }
}