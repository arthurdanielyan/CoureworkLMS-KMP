package com.coursework.featureSearchBooks.booksList

import com.coursework.corePresentation.shared.books.paging.BooksPagingSource
import com.coursework.domain.books.model.PagingLimit
import com.coursework.domain.books.model.SearchFilters
import com.coursework.domain.books.usecases.GetBooksUseCase

class SearchBooksPagingSourceFactory(
    private val getBooksUseCase: GetBooksUseCase,
) {

    operator fun invoke(
        query: String,
        filters: SearchFilters
    ): BooksPagingSource {
        return BooksPagingSource(
            getBooks = { offset, limit ->
                getBooksUseCase(
                    GetBooksUseCase.Params(
                        query = query,
                        filters = filters,
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
