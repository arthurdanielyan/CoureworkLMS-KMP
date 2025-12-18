package com.coursework.lms

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.coursework.corePresentation.navigation.LoginDestination
import com.coursework.corePresentation.navigation.SlideInFromLeft
import com.coursework.corePresentation.navigation.SlideInFromRight
import com.coursework.corePresentation.navigation.SlideOutToLeft
import com.coursework.corePresentation.navigation.SlideOutToRight
import com.coursework.corePresentation.navigation.registerNavController
import com.coursework.featureBookDetails.BookDetailsDestination
import com.coursework.featureBookDetails.common.bookDetailsNavigation
import com.coursework.featureEditBook.EditBookDestination
import com.coursework.featureEditBook.ui.EditBookScreen
import com.coursework.featureHome.HomeScreenDestination
import com.coursework.featureHome.ui.HomeScreen
import com.coursework.featureLogin.ui.LoginScreen
import com.coursework.featureSignup.signupNavigation
import kotlin.reflect.KClass

private val appScreens = mapOf<KClass<*>, @Composable (NavBackStackEntry) -> Unit>(
    EditBookDestination::class to { EditBookScreen(it.toRoute()) },
)

@Composable
internal fun RootNavigation() {
    val navController = registerNavController(RootNavigationKey)
    NavHost(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = LoginDestination,
        enterTransition = { SlideInFromRight },
        exitTransition = { SlideOutToLeft },
        popEnterTransition = { SlideInFromLeft },
        popExitTransition = { SlideOutToRight }
    ) {
        appScreens.forEach { (destinationClass, screenContent) ->
            composable(destinationClass) {
                screenContent(it)
            }
        }

        composable<LoginDestination>(
            enterTransition = { slideInHorizontally { -it } },
        ) {
            LoginScreen()
        }

        composable<HomeScreenDestination>(
            exitTransition = {
                getHomeScreenExitTransition(targetState)
            }
        ) {
            HomeScreen()
        }

        bookDetailsNavigation(
            getParentDestination = {
                val parentEntry = navController.getBackStackEntry(BookDetailsDestination::class)
                parentEntry.toRoute<BookDetailsDestination>()
            }
        )

        signupNavigation()
    }
}

fun getHomeScreenExitTransition(
    target: NavBackStackEntry
): ExitTransition {

    return when {
        target.destination.route.orEmpty()
            .startsWith(LoginDestination::class.qualifiedName.orEmpty()) -> {
            SlideOutToRight
        }

        else -> SlideOutToLeft
    }
}

const val RootNavigationKey = "root_navigation"
