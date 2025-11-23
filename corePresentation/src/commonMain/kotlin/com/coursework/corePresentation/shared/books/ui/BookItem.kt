package com.coursework.corePresentation.shared.books.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.coursework.corePresentation.commonUi.IconButton
import com.coursework.corePresentation.commonUi.SingleStarRating
import com.coursework.corePresentation.commonUi.SpacerWidth
import com.coursework.corePresentation.commonUi.contrastClickable
import com.coursework.corePresentation.shared.books.BookViewState
import commonResources.by_authors
import commonResources.ic_modify
import commonResources.ic_more
import commonResources.ic_pdf
import commonResources.ic_trash
import commonResources.modify_book
import commonResources.pdf_available
import commonResources.publisher
import commonResources.remove
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import commonResources.Res.drawable as Drawables
import commonResources.Res.string as Strings

@Immutable
class BookItemActions(
    val onModifyClick: () -> Unit,
    val onRemoveClick: () -> Unit,
)

@Composable
internal fun BookItem(
    book: BookViewState,
    onClick: () -> Unit,
    bookModifyActions: BookItemActions? = null,
) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .contrastClickable(onClick = onClick)
            .padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 16.dp,
            ),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = book.title,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                RatingBlock(
                    rating = book.rating,
                )
            }

            book.subtitle?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (book.authors.isNotEmpty()) {
                val authors = remember(book.authors) {
                    book.authors.joinToString(", ")
                }
                Text(
                    text = stringResource(Strings.by_authors, authors),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                book.publisher?.let { publisher ->
                    Text(
                        text = stringResource(Strings.publisher, publisher),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                if (book.hasPdfVersion) {
                    PdfAvailable()
                }
            }
        }
        if (bookModifyActions != null) {
            SpacerWidth(8.dp)
            BookDetailActions(
                onRemoveClick = bookModifyActions.onRemoveClick,
                onModifyClick = bookModifyActions.onModifyClick,
            )
            SpacerWidth(8.dp)
        } else {
            SpacerWidth(16.dp)
        }
    }
}

@Composable
private fun BookDetailActions(
    onRemoveClick: () -> Unit,
    onModifyClick: () -> Unit,
) {
    var isActionsExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    Box {
        IconButton(
            imageVector = vectorResource(Drawables.ic_more),
            contentDescription = "Book item additional action",
            onClick = {
                isActionsExpanded = true
            },
            tint = MaterialTheme.colorScheme.onSurface,
            iconSize = 18.dp,
        )
        DropdownMenu(
            expanded = isActionsExpanded,
            onDismissRequest = {
                isActionsExpanded = false
            },
        ) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(22.dp),
                        imageVector = vectorResource(Drawables.ic_modify),
                        contentDescription = "Edit book"
                    )
                },
                text = {
                    Text(
                        text = stringResource(Strings.modify_book),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                onClick = {
                    isActionsExpanded = false
                    onModifyClick()
                }
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(22.dp),
                        imageVector = vectorResource(Drawables.ic_trash),
                        contentDescription = "Edit book",
                    )
                },
                text = {
                    Text(
                        text = stringResource(Strings.remove),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                onClick = {
                    isActionsExpanded = false
                    onRemoveClick()
                }
            )
        }
    }
}

@Composable
private fun PdfAvailable() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = vectorResource(Drawables.ic_pdf),
            contentDescription = "PDF Available",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp),
        )
        Text(
            text = stringResource(Strings.pdf_available),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun RatingBlock(
    rating: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = formatFloat(rating),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
        )
        SingleStarRating(
            percentage = 1f,
            modifier = Modifier.size(16.dp)
        )
    }
}

private fun formatFloat(value: Float): String {
    val rounded = (value * 10).toInt()
    val integerPart = rounded / 10
    val decimalPart = rounded % 10
    return if (decimalPart == 0) "$integerPart" else "$integerPart.$decimalPart"
}

