package com.coursework.featureBookDetails.detailsScreen.mapper

import com.coursework.corePresentation.viewState.toComposeList
import com.coursework.domain.bookDetails.model.BookDetails
import com.coursework.featureBookDetails.detailsScreen.viewState.BookDetailsViewState
import com.coursework.utils.Mapper

internal class BookDetailsViewStateMapper : Mapper<BookDetails, BookDetailsViewState> {

    override fun map(from: BookDetails): BookDetailsViewState {
        return BookDetailsViewState(
            id = from.id,
            title = from.title,
            subtitle = from.subtitle,
            description = from.description,
            authors = from.authors.toComposeList(),
            publisher = from.publisher,
            publicationYear = from.publicationYear,
            edition = from.edition,
            categories = from.categories.map { it.displayName }.toComposeList(),
            hasPdfVersion = from.hasPdfVersion,
            pdfUrl = from.pdfUrl,
            coverImageUrl = from.coverImageUrl,
            totalCopies = from.totalCopies,
            copiesAvailable = from.copiesAvailable,
            language = from.language,
            isReferenceOnly = from.isReferenceOnly,
            rating = from.rating,
        )
    }
}