package com.coursework.domain.bookDetails.model.reviews

data class BookReview(
    val id: Long,
    val username: String,
    val review: String,
    val rating: Int,
)