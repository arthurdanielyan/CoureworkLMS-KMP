package com.coursework.featureHome.presentation

import androidx.compose.runtime.Immutable
import com.coursework.featureHome.presentation.viewState.BottomBarItemViewState

@Immutable
internal interface HomeUiCallbacks {

    fun onBottomTabSelected(item: BottomBarItemViewState)
    fun onBackClick()
}