package com.coursework.corePresentation.commonUi.maximizableContent

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.Density

expect fun LayoutCoordinates.getMaximizableContentAbsolutePosition(
    statusBarInsets: WindowInsets,
    density: Density
): Offset
