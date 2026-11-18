package com.coursework.corePresentation.commonUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    imeAction: ImeAction = ImeAction.Default,
    singleLine: Boolean = false,
    errorMessageEnabled: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val isPassword = keyboardType == KeyboardType.Password
    var revealedIndex by remember { mutableIntStateOf(NO_REVEAL) }
    LaunchedEffect(revealedIndex) {
        if (revealedIndex != NO_REVEAL) {
            delay(REVEAL_MILLIS)
            revealedIndex = NO_REVEAL
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TextField(
            value = value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { newValue ->
                if (isPassword) {
                    revealedIndex = typedCharIndex(old = value, new = newValue)
                }
                onValueChange(newValue)
            },
            label = {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
            ),
            isError = isError,
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction,
            ),
            singleLine = singleLine,
            visualTransformation = if (isPassword) {
                PasswordRevealTransformation(revealedIndex)
            } else {
                VisualTransformation.None
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
        )
        if (errorMessageEnabled) {
            Text(
                text = errorMessage.takeIf { isError }.orEmpty(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

private const val NO_REVEAL = -1
private const val REVEAL_MILLIS = 1_000L
private const val MASK = '•'

// Only a single typed character is shown; a paste or a deletion reveals nothing.
private fun typedCharIndex(
    old: String,
    new: String,
): Int {
    if (new.length != old.length + 1) return NO_REVEAL
    val index = new.indices.first { it == old.length || new[it] != old[it] }
    return if (new.removeRange(index, index + 1) == old) index else NO_REVEAL
}

private data class PasswordRevealTransformation(
    val revealedIndex: Int,
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val masked = buildString {
            text.forEachIndexed { index, char ->
                append(if (index == revealedIndex) char else MASK)
            }
        }
        return TransformedText(AnnotatedString(masked), OffsetMapping.Identity)
    }
}