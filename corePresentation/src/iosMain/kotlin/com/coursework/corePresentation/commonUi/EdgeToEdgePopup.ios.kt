package com.coursework.corePresentation.commonUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Popup

@Composable
actual fun EdgeToEdgePopup(
    onDismissRequest: () -> Unit,
    content: @Composable (() -> Unit)
) {
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