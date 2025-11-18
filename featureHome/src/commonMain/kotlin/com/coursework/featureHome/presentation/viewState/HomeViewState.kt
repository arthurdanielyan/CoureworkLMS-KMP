package com.coursework.featureHome.presentation.viewState

import androidx.compose.runtime.Immutable

@Immutable
internal data class HomeViewState(
    val bottomBarType: BottomBarType = BottomBarType.Student,
    val selectedItem: BottomBarItemViewState = BottomBarItemViewState.Search,
)
