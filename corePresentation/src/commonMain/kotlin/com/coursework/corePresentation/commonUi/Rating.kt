package com.coursework.corePresentation.commonUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import commonResources.ic_star_filled
import commonResources.Res.drawable as CoreDrawables
import org.jetbrains.compose.resources.vectorResource

private val PositiveColor = Color(0xFFFFF200)
private val NegativeColor = Color(0xFF717171)

@Composable
fun SingleStarRating(
    percentage: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        Icon(
            modifier = Modifier
                .wrapContentSize()
                .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                .drawWithContent {
                    drawContent()

                    drawRect(
                        topLeft = Offset.Zero,
                        size = Size(
                            width = this.size.width * percentage,
                            height = this.size.height,
                        ),
                        blendMode = BlendMode.SrcIn,
                        color = PositiveColor,
                    )
                },
            imageVector = vectorResource(CoreDrawables.ic_star_filled),
            contentDescription = null,
            tint = NegativeColor
        )
    }
}

@Composable
fun FiveStarsRating(
    rating: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SingleStarRating(percentage = (rating - 0f).coerceAtMost(1f))
        SingleStarRating(percentage = (rating - 1f).coerceAtMost(1f))
        SingleStarRating(percentage = (rating - 2f).coerceAtMost(1f))
        SingleStarRating(percentage = (rating - 3f).coerceAtMost(1f))
        SingleStarRating(percentage = (rating - 4f))
    }
}
