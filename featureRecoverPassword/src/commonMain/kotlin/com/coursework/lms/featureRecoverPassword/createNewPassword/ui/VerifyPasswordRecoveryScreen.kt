package com.coursework.lms.featureRecoverPassword.createNewPassword.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coursework.corePresentation.commonUi.PrimaryButton
import com.coursework.corePresentation.commonUi.SpacerHeight
import com.coursework.corePresentation.commonUi.TextField
import com.coursework.corePresentation.commonUi.topBar.TopBarBackButton
import com.coursework.corePresentation.shared.password.PasswordRulesBlock
import com.coursework.lms.featureRecoverPassword.createNewPassword.presentation.CreateNewPasswordUiCallbacks
import com.coursework.lms.featureRecoverPassword.createNewPassword.presentation.CreateNewPasswordViewModel
import com.coursework.lms.featureRecoverPassword.createNewPassword.presentation.CreateNewPasswordViewState
import commonResources.confirm_password
import commonResources.create_password
import commonResources.next
import commonResources.passwords_match
import lms.featurerecoverpassword.generated.resources.create_new_password
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import commonResources.Res.string as CoreStrings
import lms.featurerecoverpassword.generated.resources.Res.string as Strings


@Composable
fun CreateNewPasswordScreen() {
    val viewModel = koinViewModel<CreateNewPasswordViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CreateNewPasswordScreen(
        state = state,
        callbacks = viewModel,
    )
}

@Composable
private fun CreateNewPasswordScreen(
    state: CreateNewPasswordViewState,
    callbacks: CreateNewPasswordUiCallbacks
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TopBarBackButton(
            onClick = callbacks::onBackClick
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(Strings.create_new_password),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        SpacerHeight(12.dp)

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.passwordInput,
            onValueChange = callbacks::onPasswordType,
            isError = state.isPasswordError,
            label = stringResource(CoreStrings.create_password),
            placeholder = stringResource(CoreStrings.create_password),
            keyboardType = KeyboardType.Password,
            errorMessageEnabled = true,
        )
        PasswordRulesBlock(
            state = state.passwordRulesState,
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.passwordConfirmInput,
            onValueChange = callbacks::onPasswordConfirmType,
            isError = state.passwordsMatchError,
            label = stringResource(CoreStrings.confirm_password),
            placeholder = stringResource(CoreStrings.confirm_password),
            errorMessage = stringResource(CoreStrings.passwords_match),
            keyboardType = KeyboardType.Password,
            errorMessageEnabled = true,
        )

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(CoreStrings.next),
            enabled = state.isNextButtonEnabled,
            onClick = callbacks::onNextClick,
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}
