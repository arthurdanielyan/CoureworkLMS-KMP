package com.coursework.lms.appRouter

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.Destination
import com.coursework.corePresentation.navigation.LoginDestination
import com.coursework.corePresentation.navigation.NavControllersHolder
import com.coursework.featureBookDetails.BookDetailsDestination
import com.coursework.featureBookDetails.common.BookReviewsDestination
import com.coursework.featureEditBook.EditBookDestination
import com.coursework.featureFavorites.FavouritesDestination
import com.coursework.featureHome.HomeScreenDestination
import com.coursework.featureHome.ui.HomeBottomNavigationKey
import com.coursework.featureHome.ui.HomeNavigationKey
import com.coursework.featureMyAddedBooks.MyAddedBooksDestination
import com.coursework.featureSearchBooks.booksList.BooksListDestination
import com.coursework.featureSearchBooks.searchFilters.SearchFiltersDestination
import com.coursework.lms.RootNavigationKey
import kotlin.reflect.KClass

class AppRouterImpl : AppRouter, NavControllersHolder {

    private val controllers = linkedMapOf<String, NavHostController>()

    override fun <T : Destination> navigate(
        destination: T,
        popAll: Boolean,
        popUpTo: KClass<*>?,
        popUpToStart: Boolean,
        inclusive: Boolean,
        saveState: Boolean
    ) {
        val controller = when (destination) {
            LoginDestination,
            HomeScreenDestination,
            is BookDetailsDestination,
            BookReviewsDestination,
            is EditBookDestination -> controllers[RootNavigationKey]

            BooksListDestination,
            FavouritesDestination,
            MyAddedBooksDestination -> controllers[HomeBottomNavigationKey]

            SearchFiltersDestination -> controllers[HomeNavigationKey]

            else -> null
        }

        controller?.navigate(destination) {
            when {
                popAll -> {
                    popUpTo(controller.graph.id) {
                        this.inclusive = false
                        this.saveState = false
                    }
                }

                popUpToStart -> popUpTo(controller.graph.findStartDestination().id) {
                    this.inclusive = false
                    this.saveState = saveState
                }

                popUpTo != null -> popUpTo(popUpTo) {
                    this.inclusive = inclusive
                    this.saveState = saveState
                }
            }
            restoreState = true
            launchSingleTop = true
        }
    }

    override fun pop() {
        for (controllerEntry in controllers) {
            val controller = controllerEntry.value
            if (controller.previousBackStackEntry != null) {
                controller.popBackstackIfResumed(controller.currentBackStackEntry?.lifecycle)
                return
            }
        }
    }

    override fun register(key: String, controller: NavHostController) {
        controllers[key] = controller
    }

    override fun unregister(key: String) {
        controllers.remove(key)
    }

    private fun NavController.popBackstackIfResumed(lifecycle: Lifecycle?) {
        if (lifecycle?.currentState == Lifecycle.State.RESUMED) {
            popBackStack()
        }
    }
}