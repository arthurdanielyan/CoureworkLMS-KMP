package com.coursework.corePresentation.shared.password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import commonResources.password_rule_1
import commonResources.password_rule_2
import commonResources.password_rule_3
import org.jetbrains.compose.resources.stringResource
import commonResources.Res.string as CoreStrings

@Composable
fun PasswordRulesBlock(
    modifier: Modifier = Modifier,
    state: PasswordRulesState,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(CoreStrings.password_rule_1),
            style = MaterialTheme.typography.bodySmall,
            color = if (state.isRule1Error) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onBackground
            },
        )
        Text(
            text = stringResource(CoreStrings.password_rule_2),
            style = MaterialTheme.typography.bodySmall,
            color = if (state.isRule2Error) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onBackground
            },
        )
        Text(
            text = stringResource(CoreStrings.password_rule_3),
            style = MaterialTheme.typography.bodySmall,
            color = if (state.isRule3Error) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onBackground
            },
        )
    }
}

