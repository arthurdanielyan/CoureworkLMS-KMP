package com.coursework.corePresentation.viewState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import org.jetbrains.compose.resources.stringResource
import kotlin.jvm.JvmInline

@Immutable
sealed interface StringValue {

    data class StringResource(
        val resId: org.jetbrains.compose.resources.StringResource,
        val args: List<Any> = emptyList(),
    ) : StringValue {

        @Composable
        override fun get(): String {
            if (args.isEmpty()) {
                return stringResource(resId)
            }
            return stringResource(resId, *args.toTypedArray())
        }
    }

    @JvmInline
    value class RawString(
        val value: String,
    ) : StringValue {

        @Composable
        override fun get(): String {
            return value
        }
    }

    @Composable
    fun get(): String
}

@Composable
fun ActionWithString(stringValue: StringValue?, action: (String) -> Unit) {
    val processedString by rememberUpdatedState(stringValue?.get())

    LaunchedEffect(stringValue) {
        processedString?.let {
            action(it)
        }
    }
}