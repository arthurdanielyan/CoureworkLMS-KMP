package com.coursework.corePresentation.commonUi.filters

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.StringValue
import org.jetbrains.compose.resources.DrawableResource

@Immutable
data class FilterViewState(
    val id: Int,
    val displayName: StringValue,
    val isSelected: Boolean,
    val leadingIconResId: DrawableResource? = null,
)
