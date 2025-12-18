package com.coursework.lms.featureRecoverPassword.verifyPasswordRecovery.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coursework.corePresentation.commonUi.PrimaryButton
import com.coursework.corePresentation.commonUi.SpacerHeight
import com.coursework.corePresentation.commonUi.TextField
import com.coursework.corePresentation.commonUi.topBar.TopBarBackButton
import com.coursework.lms.featureRecoverPassword.RecoverPasswordDestination
import com.coursework.lms.featureRecoverPassword.verifyPasswordRecovery.presentation.VerifyPasswordRecoveryUiCallbacks
import com.coursework.lms.featureRecoverPassword.verifyPasswordRecovery.presentation.VerifyPasswordRecoveryViewModel
import com.coursework.lms.featureRecoverPassword.verifyPasswordRecovery.presentation.VerifyPasswordRecoveryViewState
import commonResources.next
import lms.featurerecoverpassword.generated.resources.enter_email
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import commonResources.Res.string as CoreStrings
import lms.featurerecoverpassword.generated.resources.Res.string as Strings

@Composable
fun VerifyPasswordRecoveryScreen(
    destination: RecoverPasswordDestination
) {
    val viewModel = koinViewModel<VerifyPasswordRecoveryViewModel>(
        parameters = {
            parametersOf(destination)
        }
    )
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    VerifyPasswordRecoveryScreen(
        state = state,
        callbacks = viewModel,
    )
}

@Composable
private fun VerifyPasswordRecoveryScreen(
    state: VerifyPasswordRecoveryViewState,
    callbacks: VerifyPasswordRecoveryUiCallbacks
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(16.dp),
    ) {
        TopBarBackButton(
            onClick = callbacks::onBackClick
        )
        Spacer(Modifier.weight(1f))

        Text(
            text = stringResource(Strings.enter_email),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        SpacerHeight(16.dp)
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.emailInput,
            onValueChange = callbacks::onCodeInput,
            placeholder = stringResource(Strings.enter_email),
            keyboardType = KeyboardType.Email,
            isError = state.emailMessageError != null,
            errorMessage = state.emailMessageError?.get(),
            errorMessageEnabled = true,
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
