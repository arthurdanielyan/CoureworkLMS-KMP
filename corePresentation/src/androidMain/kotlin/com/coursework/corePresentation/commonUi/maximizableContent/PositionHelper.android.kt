package com.coursework.corePresentation.commonUi.maximizableContent

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.Density

/**
 * Android's EdgeToEdgePopup implementation covers the whole screen so
 * no insets are to be respected
 */
actual fun LayoutCoordinates.getMaximizableContentAbsolutePosition(statusBarInsets: WindowInsets, density: Density): Offset {
    return positionInWindow()
}

