package com.coursework.featureSignup.verifyEmailScreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coursework.corePresentation.ShowMessages
import com.coursework.corePresentation.commonUi.PrimaryButton
import com.coursework.corePresentation.commonUi.TextButton
import com.coursework.corePresentation.commonUi.TextField
import com.coursework.corePresentation.commonUi.hideKeyboardOnTap
import com.coursework.corePresentation.commonUi.topBar.TopBarWithBackButton
import com.coursework.featureSignup.verifyEmailScreen.VerifyEmailDestination
import com.coursework.featureSignup.verifyEmailScreen.presentation.VerifyEmailUiCallbacks
import com.coursework.featureSignup.verifyEmailScreen.presentation.VerifyEmailViewModel
import com.coursework.featureSignup.verifyEmailScreen.presentation.VerifyEmailViewState
import lms.featuresignup.generated.resources.code_sent
import lms.featuresignup.generated.resources.enter_code
import lms.featuresignup.generated.resources.register
import lms.featuresignup.generated.resources.resend
import lms.featuresignup.generated.resources.verify_email
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import lms.featuresignup.generated.resources.Res.string as Strings

@Composable
fun VerifyEmailScreen(
    destination: VerifyEmailDestination,
) {
    val viewModel = koinViewModel<VerifyEmailViewModel>(
        parameters = { parametersOf(destination) }
    )
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ShowMessages(viewModel.messages)

    VerifyEmailScreen(
        state = state,
        callbacks = viewModel,
    )
}

@Composable
private fun VerifyEmailScreen(
    state: VerifyEmailViewState,
    callbacks: VerifyEmailUiCallbacks,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .hideKeyboardOnTap()
            .systemBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TopBarWithBackButton(
            onBackClick = callbacks::onBackClick,
            title = stringResource(Strings.verify_email),
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = stringResource(Strings.code_sent),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
        )
        TextButton(
            text = stringResource(Strings.resend),
            onClick = callbacks::onResendClick,
            enabled = state.isResendButtonEnabled,
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.codeInput,
            onValueChange = callbacks::onCodeType,
            label = stringResource(Strings.enter_code),
            placeholder = stringResource(Strings.enter_code),
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
            singleLine = true,
        )
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Strings.register),
            onClick = callbacks::onRegisterClick,
            enabled = state.isRegisterButtonEnabled,
        )
        Spacer(Modifier.weight(1f))
    }
}