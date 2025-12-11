package com.coursework.featureHome.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.Destination
import com.coursework.corePresentation.navigation.LoginDestination
import com.coursework.domain.user.model.UserType
import com.coursework.domain.user.usecases.GetUserTypeUseCase
import com.coursework.featureFavorites.FavouritesDestination
import com.coursework.featureHome.presentation.viewState.BottomBarItemViewState
import com.coursework.featureHome.presentation.viewState.BottomBarType
import com.coursework.featureHome.presentation.viewState.HomeViewState
import com.coursework.featureMyAddedBooks.MyAddedBooksDestination
import com.coursework.featureSearchBooks.booksList.BooksListDestination
import com.coursework.utils.stateInWhileSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getUserTypeUseCase: GetUserTypeUseCase,
    private val appRouter: AppRouter
) : ViewModel(), HomeUiCallbacks {

    private val bottomBarType = MutableStateFlow<BottomBarType>(BottomBarType.Student)
    private val selectedItem = MutableStateFlow(BottomBarItemViewState.Search)
    val uiState = combine(
        bottomBarType,
        selectedItem,
    ) { bottomBarType, selectedItem ->

        HomeViewState(
            bottomBarType = bottomBarType,
            selectedItem = selectedItem,
        )
    }.stateInWhileSubscribed(viewModelScope, HomeViewState())

    init {
        getUserType()
    }

    private fun getUserType() {
        viewModelScope.launch {
            when(getUserTypeUseCase()) {
                UserType.Student -> {
                    bottomBarType.update {
                        BottomBarType.Student
                    }
                }
                UserType.Teacher -> {
                    bottomBarType.update {
                        BottomBarType.Teacher
                    }
                }
            }
        }
    }

    private fun BottomBarItemViewState.toDestination(): Destination {
        return when (this) {
            BottomBarItemViewState.Search -> BooksListDestination
            BottomBarItemViewState.Favourites -> FavouritesDestination
            BottomBarItemViewState.MyBooks -> MyAddedBooksDestination
        }
    }

    override fun onBottomTabSelected(item: BottomBarItemViewState) {
        appRouter.navigate(
            destination = item.toDestination(),
            popUpToStart = true,
            saveState = true
        )
        selectedItem.update {
            item
        }
    }

    override fun onLogoutClick() {
        // TODO: Implement logout logic
        appRouter.navigate(
            destination = LoginDestination,
            popAll = true,
        )
    }

    override fun onBackClick() {
        appRouter.navigate(
            destination = BooksListDestination,
            popUpToStart = true,
            saveState = true
        )
        selectedItem.update {
            BottomBarItemViewState.Search
        }
    }
}