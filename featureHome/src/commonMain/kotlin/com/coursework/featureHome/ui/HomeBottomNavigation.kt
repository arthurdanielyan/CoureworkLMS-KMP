package com.coursework.featureHome.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coursework.corePresentation.navigation.registerNavController
import com.coursework.featureFavorites.FavouritesDestination
import com.coursework.featureFavorites.ui.FavouritesScreen
import com.coursework.featureHome.presentation.HomeUiCallbacks
import com.coursework.featureHome.presentation.HomeViewModel
import com.coursework.featureMyAddedBooks.MyAddedBooksDestination
import com.coursework.featureMyAddedBooks.ui.MyAddedBooksScreen
import com.coursework.featureSearchBooks.booksList.BooksListDestination
import com.coursework.featureSearchBooks.booksList.ui.SearchBooksScreen
import com.coursework.featureSearchBooks.shared.SearchBooksSharedViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeBottomNavigationScreens(searchBooksSharedViewModel: SearchBooksSharedViewModel) {

    val viewModel = koinViewModel<HomeViewModel>()
    val callbacks: HomeUiCallbacks = viewModel

    val navController = registerNavController(HomeBottomNavigationKey)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HomeTopBar(
            onLogoutClick = callbacks::onLogoutClick
        )
        NavHost(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.background),
            navController = navController,
            startDestination = BooksListDestination,
            enterTransition = { fadeIn(tween(500)) },
            popEnterTransition = { fadeIn(tween(500)) },
            exitTransition = { fadeOut(tween(500)) },
            popExitTransition = { fadeOut(tween(500)) }
        ) {
            composable<BooksListDestination> {
                SearchBooksScreen(
                    sharedViewModel = searchBooksSharedViewModel
                )
            }

            composable<MyAddedBooksDestination> {
                BackHandler {
                    callbacks.onBackClick()
                }
                MyAddedBooksScreen()
            }

            composable<FavouritesDestination> {
                BackHandler {
                    callbacks.onBackClick()
                }
                FavouritesScreen()
            }
        }
    }
}

const val HomeBottomNavigationKey = "home_bottom_navigation"
