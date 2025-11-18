package com.coursework.featureHome.presentation.viewState

import androidx.compose.runtime.Immutable
import lms.featurehome.generated.resources.favourites
import lms.featurehome.generated.resources.ic_books
import lms.featurehome.generated.resources.ic_favourites
import lms.featurehome.generated.resources.ic_search
import lms.featurehome.generated.resources.my_books
import lms.featurehome.generated.resources.search
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import lms.featurehome.generated.resources.Res.drawable as Drawables
import lms.featurehome.generated.resources.Res.string as Strings

@Immutable
internal sealed interface BottomBarType {

    val items: List<BottomBarItemViewState>

    data object Student : BottomBarType {
        override val items = listOf(
            BottomBarItemViewState.Search,
            BottomBarItemViewState.Favourites,
        )
    }

    data object Teacher : BottomBarType {
        override val items = listOf(
            BottomBarItemViewState.Search,
            BottomBarItemViewState.MyBooks,
        )
    }
}

internal enum class BottomBarItemViewState(
    val titleKey: StringResource,
    val icon: DrawableResource,
) {

    Search(
        titleKey = Strings.search,
        icon = Drawables.ic_search,
    ),

    Favourites(
        titleKey = Strings.favourites,
        icon = Drawables.ic_favourites,
    ),

    MyBooks(
        titleKey = Strings.my_books,
        icon = Drawables.ic_books,
    ),
}