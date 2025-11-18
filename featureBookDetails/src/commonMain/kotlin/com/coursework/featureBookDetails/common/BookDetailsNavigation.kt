package com.coursework.featureBookDetails.common

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.coursework.corePresentation.Destination
import com.coursework.featureBookDetails.BookDetailsDestination
import com.coursework.featureBookDetails.detailsScreen.ui.BookDetailsScreen
import com.coursework.featureBookDetails.reviewsScreen.ui.BookReviewsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.bookDetailsNavigation(getParentDestination: () -> BookDetailsDestination) {
    navigation<BookDetailsDestination>(
        startDestination = BookDetailsInnerDestination
    ) {
        composable<BookDetailsInnerDestination> {
            val bookDetailsDestination = remember {
                getParentDestination()
            }
            BookDetailsScreen(bookDetailsDestination)
        }

        composable<BookReviewsDestination> {
            val bookDetailsDestination = remember {
                getParentDestination()
            }
            BookReviewsScreen(bookDetailsDestination)
        }
    }
}

@Serializable
internal data object BookDetailsInnerDestination : Destination

@Serializable
data object BookReviewsDestination : Destination

