package com.coursework.lms

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIRectEdgeAll

fun MainViewController() =
    ComposeUIViewController(
        configure = {
            initKoin()
        }
    ) {
        App()
    }