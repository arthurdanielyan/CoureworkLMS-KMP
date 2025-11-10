package com.coursework.corePresentation.commonUi.maximizableContent

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.Density
import kotlinx.cinterop.ExperimentalForeignApi
import kotlin.math.abs

@OptIn(ExperimentalForeignApi::class)
actual fun LayoutCoordinates.getMaximizableContentAbsolutePosition(statusBarInsets: WindowInsets, density: Density): Offset {

    val statusBarHeight = abs(statusBarInsets.getBottom(density) - statusBarInsets.getTop(density))

    return positionInWindow().let {
        it.copy(y = it.y - statusBarHeight)
    }
}
