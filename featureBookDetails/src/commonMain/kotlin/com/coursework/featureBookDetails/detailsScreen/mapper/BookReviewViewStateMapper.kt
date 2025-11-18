package com.coursework.featureBookDetails.detailsScreen.mapper

import com.coursework.domain.bookDetails.model.reviews.BookReview
import com.coursework.featureBookDetails.common.viewState.BookReviewViewState
import com.coursework.utils.Mapper

internal class BookReviewViewStateMapper : Mapper<BookReview, BookReviewViewState> {

    override fun map(from: BookReview): BookReviewViewState {
        return BookReviewViewState(
            id = from.id,
            username = from.username,
            review = from.review,
            rating = from.rating,
        )
    }
}