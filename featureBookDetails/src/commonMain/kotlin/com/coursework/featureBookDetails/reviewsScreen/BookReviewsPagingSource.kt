package com.coursework.featureBookDetails.reviewsScreen

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.coursework.domain.bookDetails.model.reviews.BookReview
import com.coursework.domain.bookDetails.usecases.GetBookReviewsUseCase
import com.coursework.domain.books.model.PagingLimit

internal class BookReviewsPagingSource(
    private val bookId: Long,
    private val filterId: Int,
    private val getBookReviewsUseCase: GetBookReviewsUseCase,
) : PagingSource<Int, BookReview>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookReview> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        val result = getBookReviewsUseCase(
            GetBookReviewsUseCase.Params(
                bookId = bookId,
                filterId = filterId,
                pagingLimit = PagingLimit(
                    offset = offset,
                    limit = limit,
                )
            )
        )


        return result
            .map {
                val books = it.reviews
                LoadResult.Page(
                    data = books,
                    prevKey = if (offset == 0) null else offset - limit,
                    nextKey = if (it.isEndReached) null else offset + limit
                )
            }.getOrElse {
                LoadResult.Error(it)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, BookReview>): Int {
        return 0
    }

    class Factory(
        private val getBookReviewsUseCase: GetBookReviewsUseCase
    ) {
        operator fun invoke(
            bookId: Long,
            filterId: Int,
        ): BookReviewsPagingSource {
            return BookReviewsPagingSource(
                bookId = bookId,
                filterId = filterId,
                getBookReviewsUseCase = getBookReviewsUseCase,
            )
        }
    }
}