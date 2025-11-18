package com.coursework.domain.bookDetails.usecases

import com.coursework.domain.bookDetails.BookDetailsRepository
import com.coursework.domain.bookDetails.model.BookReviewPaginationResult
import com.coursework.domain.books.model.PagingLimit

class GetBookReviewsUseCase(
    val repository: BookDetailsRepository
) {

    data class Params(
        val bookId: Long,
        val filterId: Int? = null,
        val pagingLimit: PagingLimit,
    )

    suspend operator fun invoke(params: Params): Result<BookReviewPaginationResult> {
        return repository.getReviews(
            bookId = params.bookId,
            pagingLimit = params.pagingLimit,
        )
    }
}
