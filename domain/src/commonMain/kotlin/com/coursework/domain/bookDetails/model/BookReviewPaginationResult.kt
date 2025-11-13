package com.coursework.domain.bookDetails.model

import com.coursework.domain.bookDetails.model.reviews.BookReview

data class BookReviewPaginationResult(
    val reviews: List<BookReview>,
    val isEndReached: Boolean
)