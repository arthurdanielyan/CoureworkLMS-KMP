package com.coursework.corePresentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import coil3.compose.LocalPlatformContext
import com.coursework.corePresentation.extensions.ObserveEffects
import com.coursework.corePresentation.viewState.StringValue
import kotlinx.coroutines.flow.Flow

@Composable
fun ShowMessages(messages: Flow<StringValue>) {
    val context = LocalPlatformContext.current
    var shown by remember { mutableStateOf<ShownMessage?>(null) }
    val text = shown?.message?.get()

    // Keyed on the wrapper, which is new each time, so the same message twice in a row still shows twice.
    LaunchedEffect(shown) {
        text?.let { showError(it, context) }
    }
    ObserveEffects(messages) { message ->
        shown = ShownMessage(message)
    }
}

private class ShownMessage(
    val message: StringValue,
)
