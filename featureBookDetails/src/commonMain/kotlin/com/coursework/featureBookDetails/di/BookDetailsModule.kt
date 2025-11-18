package com.coursework.featureBookDetails.di

import com.coursework.featureBookDetails.detailsScreen.BookDetailsViewModel
import com.coursework.featureBookDetails.detailsScreen.mapper.BookDetailsViewStateMapper
import com.coursework.featureBookDetails.detailsScreen.mapper.BookReviewViewStateMapper
import com.coursework.featureBookDetails.detailsScreen.mapper.RatingDistributionViewStateMapper
import com.coursework.featureBookDetails.reviewsScreen.BookReviewsPagingSource
import com.coursework.featureBookDetails.reviewsScreen.BookReviewsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureBookDetailsModule = module {
    viewModelOf(::BookDetailsViewModel)

    factoryOf(::BookDetailsViewStateMapper)
    factoryOf(::BookReviewViewStateMapper)
    factoryOf(BookReviewsPagingSource::Factory)
    factoryOf(::RatingDistributionViewStateMapper)

    viewModelOf(::BookReviewsViewModel)
    factoryOf(BookReviewsPagingSource::Factory)
}