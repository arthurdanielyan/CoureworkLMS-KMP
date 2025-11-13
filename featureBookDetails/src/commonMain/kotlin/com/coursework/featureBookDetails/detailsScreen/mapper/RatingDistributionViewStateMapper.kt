package com.coursework.featureBookDetails.detailsScreen.mapper

import com.coursework.domain.bookDetails.model.reviews.BookRatingDistribution
import com.coursework.featureBookDetails.common.viewState.RatingDistributionViewState
import com.coursework.utils.Mapper

class RatingDistributionViewStateMapper :
    Mapper<BookRatingDistribution, RatingDistributionViewState> {

    override fun map(from: BookRatingDistribution): RatingDistributionViewState {
        return RatingDistributionViewState(
            oneStar = from.oneStar,
            twoStars = from.twoStars,
            threeStars = from.threeStars,
            fourStars = from.fourStars,
            fiveStars = from.fiveStars,
        )
    }
}