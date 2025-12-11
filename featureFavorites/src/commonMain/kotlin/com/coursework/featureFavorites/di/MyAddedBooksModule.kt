package com.coursework.featureFavorites.di

import com.coursework.featureFavorites.presentation.FavouritesPagingSourceFactory
import com.coursework.featureFavorites.presentation.FavouritesViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val favouritesModule = module {
    viewModelOf(::FavouritesViewModel)

    factoryOf(::FavouritesPagingSourceFactory)
}