package com.coursework.domain.books.usecases

import com.coursework.domain.books.BooksRepository
import com.coursework.domain.books.model.PagingLimit
import com.coursework.domain.books.model.books.BookPaginationResult

class GetMyAddedBooks(
    private var repository: BooksRepository
) {

    data class Params(
        val pagingLimit: PagingLimit,
    )

    suspend operator fun invoke(params: Params): Result<BookPaginationResult> {
        return repository.getMyAddedBooks(pagingLimit = params.pagingLimit)
    }
}