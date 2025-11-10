package com.coursework.corePresentation.commonUi.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.coursework.corePresentation.viewState.ComposeList
import commonResources.ic_more
import commonResources.show_all
import commonResources.Res.string as CoreStrings
import commonResources.Res.drawable as CoreDrawables
import org.jetbrains.compose.resources.stringResource

@Composable
fun FilterSection(
    title: String,
    filters: ComposeList<FilterViewState>,
    onFilterSelect: (FilterViewState) -> Unit,
    modifier: Modifier = Modifier,
    onShowAllClick: () -> Unit = {},
    showShowAllButton: Boolean = false,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            filters.fastForEach { filter ->
                FilterChip(
                    filter = filter,
                    onSelect = {
                        onFilterSelect(filter)
                    },
                )
            }
            if (showShowAllButton) {
                ChipButton(
                    text = stringResource(CoreStrings.show_all),
                    onClick = onShowAllClick,
                    leadingIconResId = CoreDrawables.ic_more,
                )
            }
        }
    }
}