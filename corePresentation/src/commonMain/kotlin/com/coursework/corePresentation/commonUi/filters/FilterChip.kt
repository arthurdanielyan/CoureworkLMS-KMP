package com.coursework.corePresentation.commonUi.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun FilterChip(
    filter: FilterViewState,
    onSelect: () -> Unit,
) {
    BaseChip(
        text = filter.displayName.get(),
        onClick = onSelect,
        isSelected = filter.isSelected,
        leadingIconResId = filter.leadingIconResId,
    )
}

@Composable
fun ChipButton(
    text: String,
    onClick: () -> Unit,
    leadingIconResId: DrawableResource? = null,
) {
    BaseChip(
        text = text,
        onClick = onClick,
        isSelected = false,
        leadingIconResId = leadingIconResId,
    )
}

@Composable
private fun BaseChip(
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    leadingIconResId: DrawableResource? = null,
) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(
                if (isSelected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.tertiaryContainer
                },
            )
            .clickable(onClick = onClick)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            alignment = Alignment.CenterHorizontally,
            space = 8.dp
        ),
    ) {
        if (leadingIconResId != null) {
            Icon(
                imageVector = vectorResource(leadingIconResId),
                contentDescription = null,
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.onSecondaryContainer
                } else {
                    MaterialTheme.colorScheme.onTertiaryContainer
                },
                modifier = Modifier
                    .requiredSize(18.dp)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = if (isSelected) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.onTertiaryContainer
            },
        )
    }
}
