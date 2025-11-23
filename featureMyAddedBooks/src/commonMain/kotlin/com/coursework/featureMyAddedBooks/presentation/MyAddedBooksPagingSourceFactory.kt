package com.coursework.featureMyAddedBooks.presentation

import com.coursework.corePresentation.shared.books.paging.BooksPagingSource
import com.coursework.domain.books.model.PagingLimit
import com.coursework.domain.books.usecases.GetMyAddedBooks

class MyAddedBooksPagingSourceFactory(
    private val getMyAddedBooks: GetMyAddedBooks,
) {

    operator fun invoke(): BooksPagingSource {
        return BooksPagingSource(
            getBooks = { offset, limit ->
                getMyAddedBooks(
                    GetMyAddedBooks.Params(
                        pagingLimit = PagingLimit(
                            offset = offset,
                            limit = limit,
                        )
                    )
                )
            }
        )
    }
}