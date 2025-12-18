package com.coursework.lms.featureRecoverPassword.verifyCode.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coursework.corePresentation.commonUi.PrimaryButton
import com.coursework.corePresentation.commonUi.SpacerHeight
import com.coursework.corePresentation.commonUi.TextField
import com.coursework.corePresentation.commonUi.topBar.TopBarBackButton
import com.coursework.lms.featureRecoverPassword.verifyCode.presentation.VerifyCodeUiCallbacks
import com.coursework.lms.featureRecoverPassword.verifyCode.presentation.VerifyCodeViewModel
import com.coursework.lms.featureRecoverPassword.verifyCode.presentation.VerifyCodeViewState
import commonResources.next
import lms.featurerecoverpassword.generated.resources.code_input_placeholder
import lms.featurerecoverpassword.generated.resources.enter_code
import lms.featurerecoverpassword.generated.resources.enter_code_description
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import commonResources.Res.string as CoreStrings
import lms.featurerecoverpassword.generated.resources.Res.string as Strings

@Composable
fun VerifyCodeScreen() {
    val viewModel = koinViewModel<VerifyCodeViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    VerifyCodeScreen(
        state = state,
        callbacks = viewModel,
    )
}

@Composable
private fun VerifyCodeScreen(
    state: VerifyCodeViewState,
    callbacks: VerifyCodeUiCallbacks,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TopBarBackButton(
            onClick = callbacks::onBackClick,
        )
        Spacer(Modifier.weight(1f))

        Text(
            text = stringResource(Strings.enter_code_description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        SpacerHeight(16.dp)
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.codeInput,
            onValueChange = callbacks::onCodeType,
            label = stringResource(Strings.enter_code),
            placeholder = stringResource(Strings.code_input_placeholder),
            keyboardType = KeyboardType.Text,
        )
        SpacerHeight(16.dp)
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(CoreStrings.next),
            enabled = state.isNextButtonEnabled,
            onClick = callbacks::onNextClick,
        )

        Spacer(Modifier.weight(2f))
    }
}


