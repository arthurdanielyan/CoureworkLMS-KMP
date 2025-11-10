package com.coursework.lms

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import coil3.compose.LocalPlatformContext
import com.coursework.corePresentation.navigation.SlideInFromLeft
import com.coursework.corePresentation.navigation.SlideInFromRight
import com.coursework.corePresentation.navigation.SlideOutToLeft
import com.coursework.corePresentation.navigation.SlideOutToRight
import com.coursework.corePresentation.navigation.destinations.BookDetailsDestination
import com.coursework.corePresentation.navigation.destinations.EditBookDestination
import com.coursework.corePresentation.navigation.destinations.HomeScreenDestination
import com.coursework.corePresentation.navigation.destinations.LoginDestination
import com.coursework.corePresentation.navigation.registerNavController
import com.coursework.featureBookDetails.ui.BookDetailsScreen
import com.coursework.featureEditBook.ui.EditBookScreen
import com.coursework.featureHome.ui.HomeScreen
import com.coursework.featureLogin.ui.LoginScreen
import kotlin.reflect.KClass

private val appScreens = mapOf<KClass<*>, @Composable (NavBackStackEntry) -> Unit>(
    BookDetailsDestination::class to { BookDetailsScreen(it.toRoute()) },
    EditBookDestination::class to { EditBookScreen(it.toRoute()) },
)

@Composable
internal fun RootNavigation() {
    val navController = registerNavController(RootNavigationKey)
    LocalPlatformContext
    NavHost(
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

@Composable
private fun DummyScreen(title: String, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(title, Modifier.clickable(onClick = onClick), color = MaterialTheme.colorScheme.onBackground)
    }
}

const val RootNavigationKey = "root_navigation"
