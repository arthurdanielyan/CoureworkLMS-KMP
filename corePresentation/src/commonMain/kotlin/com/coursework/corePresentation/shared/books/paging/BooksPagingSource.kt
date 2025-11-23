package com.coursework.corePresentation.shared.books.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.coursework.domain.books.model.books.Book
import com.coursework.domain.books.model.books.BookPaginationResult

class BooksPagingSource(
    private val getBooks: suspend (offset: Int, limit: Int) -> Result<BookPaginationResult>,
) : PagingSource<Int, Book>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        val result = getBooks(offset, limit)

        return result
            .map {
                val books = it.books
                LoadResult.Page(
                    data = books,
                    prevKey = if (offset == 0) null else offset - limit,
                    nextKey = if (it.isEndReached) null else offset + limit
                )
            }.getOrElse {
                LoadResult.Error(it)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return 0
    }
}