package com.coursework.corePresentation.commonUi.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coursework.corePresentation.viewState.ComposeList

@Composable
fun FilterChipRow(
    filters: ComposeList<FilterViewState>,
    onFilterSelect: (FilterViewState) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
) {
    LazyRow(
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement,
    ) {
        items(filters) { filter ->
            FilterChip(
                filter = filter,
                onSelect = {
                    onFilterSelect(filter)
                },
            )
        }
    }
}