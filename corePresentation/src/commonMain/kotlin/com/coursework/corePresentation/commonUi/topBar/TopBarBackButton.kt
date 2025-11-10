package com.coursework.corePresentation.commonUi.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import commonResources.Res.drawable as CoreDrawables
import commonResources.ic_back
import org.jetbrains.compose.resources.vectorResource

@Composable
fun TopBarBackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier
            .requiredSize(ButtonSize)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.small,
            ),
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier
                .requiredSize(IconSize),
            imageVector = vectorResource(CoreDrawables.ic_back), // TODO: change
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

private val ButtonSize = 40.dp
private val IconSize = 28.dp
