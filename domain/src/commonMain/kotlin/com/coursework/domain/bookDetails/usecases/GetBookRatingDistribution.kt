package com.coursework.domain.bookDetails.usecases

import com.coursework.domain.bookDetails.BookDetailsRepository
import com.coursework.domain.bookDetails.model.reviews.BookRatingDistribution

class GetBookRatingDistribution(
    private val repository: BookDetailsRepository,
) {

    suspend operator fun invoke(bookId: Long): Result<BookRatingDistribution> {
        return repository.getBookRatingDistribution(bookId)
    }
}