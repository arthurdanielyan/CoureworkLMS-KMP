package com.coursework.corePresentation.commonUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.window.Popup

@Composable
actual fun EdgeToEdgePopup(
    onDismissRequest: () -> Unit,
    content: @Composable (() -> Unit)
) {
    val window = LocalWindowInfo.current
    val density = LocalDensity.current
    val screenHeight = remember(window, density) {
        density.run { window.containerSize.height.toDp() }
    }
    Popup(
        onDismissRequest = onDismissRequest
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            content()
        }
    }
}