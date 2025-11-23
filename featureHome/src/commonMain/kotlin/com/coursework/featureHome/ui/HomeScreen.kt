package com.coursework.featureHome.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coursework.corePresentation.navigation.Destination
import com.coursework.corePresentation.navigation.SlideInFromLeft
import com.coursework.corePresentation.navigation.SlideInFromRight
import com.coursework.corePresentation.navigation.SlideOutToLeft
import com.coursework.corePresentation.navigation.SlideOutToRight
import com.coursework.corePresentation.navigation.registerNavController
import com.coursework.featureHome.presentation.HomeUiCallbacks
import com.coursework.featureHome.presentation.HomeViewModel
import com.coursework.featureHome.ui.bottomBar.BottomBar
import com.coursework.featureSearchBooks.searchFilters.SearchFiltersDestination
import com.coursework.featureSearchBooks.searchFilters.ui.SearchFiltersScreen
import com.coursework.featureSearchBooks.shared.SearchBooksSharedViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object HomeBottomNavigationDestination : Destination

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen() {

    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val callbacks: HomeUiCallbacks = viewModel

    val navController = registerNavController(HomeNavigationKey)

    val searchBooksSharedViewModel = koinViewModel<SearchBooksSharedViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        NavHost(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.background),
            navController = navController,
            startDestination = HomeBottomNavigationDestination,
        ) {
            composable<HomeBottomNavigationDestination>(
                enterTransition = { SlideInFromRight },
                popExitTransition = { SlideOutToRight },
                exitTransition = { SlideOutToLeft },
                popEnterTransition = { SlideInFromLeft }
            ) {
                HomeBottomNavigationScreens(searchBooksSharedViewModel)
            }
            composable<SearchFiltersDestination>(
                enterTransition = { SlideInFromRight },
                popExitTransition = { SlideOutToRight }
            ) {
                SearchFiltersScreen(
                    sharedViewModel = searchBooksSharedViewModel
                )
            }
        }
        BottomBar(
            bottomBarType = state.bottomBarType,
            selectedItem = state.selectedItem,
            onTabSelected = callbacks::onBottomTabSelected
        )
    }
}

const val HomeNavigationKey = "home_navigation"