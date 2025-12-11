package com.coursework.corePresentation.commonUi.filters

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    val lazyListState = rememberLazyListState()
    val selectedFilterIndex by remember(filters) {
        derivedStateOf {
            filters.indexOfFirst { it.isSelected }
        }
    }
    LaunchedEffect(selectedFilterIndex) {
        with(lazyListState) {
            if(firstVisibleItemIndex == selectedFilterIndex) {
                val offset = layoutInfo.visibleItemsInfo[firstVisibleItemIndex].offset
//                println("yapping $offset index $selectedFilterIndex")
//                if(firstVisibleItemScrollOffset < layoutInfo.viewportStartOffset) {
//                    lazyListState.scrollToItem(firstVisibleItemIndex)
//                }
            }
        }
    }
    LaunchedEffect(selectedFilterIndex) {
        with(lazyListState) {
            val selectedIndex = (selectedFilterIndex-firstVisibleItemIndex).coerceIn(0, layoutInfo.visibleItemsInfo.lastIndex)
            val offset = layoutInfo.visibleItemsInfo[selectedIndex].offset
            val size = layoutInfo.visibleItemsInfo[selectedIndex].size
            println("yapping offset: ${offset + size}, viewportEndOffset: ${layoutInfo.viewportEndOffset}")
            if(layoutInfo.isFullyVisible(layoutInfo.visibleItemsInfo[selectedIndex]).not()) {
                lazyListState.animateScrollToItem(selectedFilterIndex)
            }
        }
    }
    LazyRow(
        state = lazyListState,
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

val LazyListLayoutInfo.mainAxisSize: Int
    get() {
        return when(orientation) {
            Orientation.Vertical -> viewportSize.height
            Orientation.Horizontal -> viewportSize.width
        }
    }

fun LazyListLayoutInfo.isFullyVisible(item: LazyListItemInfo): Boolean {
    return item.offset + item.size - viewportStartOffset < viewportEndOffset && item.offset > viewportStartOffset
}
