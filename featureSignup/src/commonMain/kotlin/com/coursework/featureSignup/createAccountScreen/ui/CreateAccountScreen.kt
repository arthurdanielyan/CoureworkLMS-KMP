package com.coursework.featureSignup.createAccountScreen.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coursework.corePresentation.ShowMessages
import com.coursework.corePresentation.commonUi.PrimaryButton
import com.coursework.corePresentation.commonUi.SecondaryButton
import com.coursework.corePresentation.commonUi.SpacerHeight
import com.coursework.corePresentation.commonUi.TextField
import com.coursework.corePresentation.shared.password.PasswordRulesBlock
import com.coursework.featureSignup.createAccountScreen.presentation.CreateAccountUiCallbacks
import com.coursework.featureSignup.createAccountScreen.presentation.CreateAccountViewModel
import com.coursework.featureSignup.createAccountScreen.presentation.CreateAccountViewState
import commonResources.confirm_password
import commonResources.create_password
import commonResources.email
import commonResources.enter_email
import commonResources.next
import commonResources.passwords_match
import commonResources.signup
import lms.featuresignup.generated.resources.enter_full_name
import lms.featuresignup.generated.resources.enter_polytech_email
import lms.featuresignup.generated.resources.full_name
import lms.featuresignup.generated.resources.sign_in
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import commonResources.Res.string as CoreStrings
import lms.featuresignup.generated.resources.Res.string as Strings

@Composable
fun CreateAccountScreen() {
    val viewModel = koinViewModel<CreateAccountViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ShowMessages(viewModel.errorMessages)

    CreateAccountScreen(
        state = state,
        callbacks = viewModel,
    )
}

@Composable
private fun CreateAccountScreen(
    state: CreateAccountViewState,
    callbacks: CreateAccountUiCallbacks,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(CoreStrings.signup),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = stringResource(Strings.enter_polytech_email),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        SpacerHeight(16.dp)
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.emailInput,
            onValueChange = callbacks::onEmailType,
            isError = state.isEmailInputError,
            errorMessage = state.emailErrorMessage?.get(),
            label = stringResource(CoreStrings.email),
            placeholder = stringResource(CoreStrings.enter_email),
            keyboardType = KeyboardType.Email,
            errorMessageEnabled = true,
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.fullNameInput,
            onValueChange = callbacks::onFullNameType,
            label = stringResource(Strings.full_name),
            placeholder = stringResource(Strings.enter_full_name),
            keyboardType = KeyboardType.Text,
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.passwordInput,
            onValueChange = callbacks::onPasswordType,
            isError = state.isPasswordError,
            label = stringResource(CoreStrings.create_password),
            placeholder = stringResource(CoreStrings.create_password),
            keyboardType = KeyboardType.Text,
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
            keyboardType = KeyboardType.Text,
            errorMessageEnabled = true,
        )
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(CoreStrings.next),
            onClick = callbacks::onNextClick,
            enabled = state.isNextButtonEnabled,
        )
        Spacer(Modifier.weight(1f))
        SecondaryButton(
            text = stringResource(Strings.sign_in),
            onClick = callbacks::onLoginClick
        )
    }
}
