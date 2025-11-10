package com.coursework.featureHome.presentation

import androidx.compose.runtime.Immutable
import com.coursework.featureHome.presentation.viewState.BottomBarItemViewState

@Immutable
interface HomeUiCallbacks {

    fun onBottomTabSelected(item: BottomBarItemViewState)
    fun onBackClick()
}