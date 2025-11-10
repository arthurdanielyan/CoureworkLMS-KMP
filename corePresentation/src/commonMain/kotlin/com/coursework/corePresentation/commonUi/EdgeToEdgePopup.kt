package com.coursework.corePresentation.commonUi

import androidx.compose.runtime.Composable

@Composable
expect fun EdgeToEdgePopup(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
)
