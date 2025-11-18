package com.coursework.corePresentation.commonUi

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.coursework.corePresentation.viewState.getErrorMessage
import commonResources.retry
import org.jetbrains.compose.resources.stringResource
import commonResources.Res.string as Strings

fun <T : Any> LazyListScope.pagingBottomContent(
    pagingItems: LazyPagingItems<T>
) {
    when (val loadState = pagingItems.loadState.append) {
        is LoadState.Loading ->
            item {
                CircularProgressIndicator()
            }

        is LoadState.Error ->
            item {
                Text(
                    text = getErrorMessage(loadState.error).get(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleSmall,
                )
                SpacerHeight(16.dp)
                PrimaryButton(
                    text = stringResource(Strings.retry),
                    onClick = pagingItems::retry
                )
            }

        else -> Unit
    }
}