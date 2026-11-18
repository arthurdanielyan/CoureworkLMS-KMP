package com.coursework.corePresentation.commonUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = { if (isLoading.not()) onClick() },
    ) {
        ButtonContent(
            text = text,
            isLoading = isLoading,
        )
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick
    ) {
        Text(
            text = text,
        )
    }
}

@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    TextButton(
        modifier = modifier,
        enabled = enabled,
        onClick = { if (isLoading.not()) onClick() },
    ) {
        ButtonContent(
            text = text,
            isLoading = isLoading,
        )
    }
}

@Composable
private fun ButtonContent(
    text: String,
    isLoading: Boolean,
) {
    Box(contentAlignment = Alignment.Center) {
        // The text stays laid out while hidden, so the button keeps its size when the spinner replaces it.
        Text(
            modifier = Modifier.alpha(if (isLoading) 0f else 1f),
            text = text,
        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                color = LocalContentColor.current,
                strokeWidth = 2.dp,
            )
        }
    }
}
