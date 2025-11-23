package com.coursework.featureEditBook.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.size.Size
import com.coursework.corePresentation.commonUi.IconButton
import com.coursework.corePresentation.commonUi.SpacerWidth
import com.coursework.corePresentation.commonUi.maximizableContent.MaximizableContent
import com.coursework.corePresentation.commonUi.modifyIf
import com.coursework.featureEditBook.presentation.viewState.CoverImageViewState
import commonResources.ic_close
import commonResources.ic_modify
import commonResources.ic_trash
import lms.featureeditbook.generated.resources.cover_uploaded
import lms.featureeditbook.generated.resources.ic_upload
import lms.featureeditbook.generated.resources.upload_cover_image
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import commonResources.Res.drawable as CoreDrawables
import lms.featureeditbook.generated.resources.Res.drawable as Drawables
import lms.featureeditbook.generated.resources.Res.string as Strings

@Composable
internal fun CoverImagePicker(
    modifier: Modifier = Modifier,
    coverImage: CoverImageViewState,
    onPickImageClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    var isImageMaximized by rememberSaveable(stateSaver = autoSaver()) {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier
            .modifyIf(coverImage.isUploaded.not()) {
                clickable(onClick = onPickImageClick)
            }
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.medium,
            )
            .animateContentSize()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (coverImage.isUploaded) {
            MaximizableContent(
                isMaximized = isImageMaximized,
                onToggle = { isImageMaximized = !isImageMaximized },
                overlayContent = {
                    Box(
                        modifier = Modifier
                            .systemBarsPadding()
                            .align(Alignment.TopEnd)
                    ) {
                        IconButton(
                            modifier = Modifier
                                .padding(16.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.tertiaryContainer),
                            imageVector = vectorResource(CoreDrawables.ic_close),
                            contentDescription = "Minimize",
                            onClick = {
                                isImageMaximized = false
                            },
                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(coverImage.url ?: coverImage.localUri)
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .requiredWidth(36.dp)
                )
            }
        } else {
            Icon(
                imageVector = vectorResource(Drawables.ic_upload),
                contentDescription = null,
                modifier = Modifier.requiredSize(28.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        SpacerWidth(8.dp)
        Text(
            text = stringResource(
                if (coverImage.isUploaded) {
                    Strings.cover_uploaded
                } else {
                    Strings.upload_cover_image
                }
            ),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
        )
        SpacerWidth(2.dp)
        if (coverImage.isUploaded) {
            IconButton(
                imageVector = vectorResource(CoreDrawables.ic_modify),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                onClick = onPickImageClick
            )
            SpacerWidth(2.dp)
            IconButton(
                imageVector = vectorResource(CoreDrawables.ic_trash),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                onClick = onRemoveClick
            )
        }
    }
}

