package com.coursework.featureLogin.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coursework.corePresentation.commonUi.PrimaryButton
import com.coursework.corePresentation.commonUi.SecondaryButton
import com.coursework.corePresentation.commonUi.SpacerHeight
import com.coursework.corePresentation.commonUi.TextField
import com.coursework.featureLogin.presentation.LoginUiCallbacks
import com.coursework.featureLogin.presentation.LoginViewModel
import com.coursework.featureLogin.presentation.LoginViewState
import commonResources.email
import commonResources.enter_email
import commonResources.signup
import lms.featurelogin.generated.resources.enter_password
import lms.featurelogin.generated.resources.login
import lms.featurelogin.generated.resources.password
import lms.featurelogin.generated.resources.welcome_back
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import commonResources.Res.string as CoreStrings
import lms.featurelogin.generated.resources.Res.string as Strings

@Composable
fun LoginScreen() {
    val viewmodel = koinViewModel<LoginViewModel>()
    val state by viewmodel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        callbacks = viewmodel,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun LoginScreen(
    state: LoginViewState,
    callbacks: LoginUiCallbacks
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Strings.welcome_back),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.weight(1f))
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
        SpacerHeight(8.dp)
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = state.passwordInput,
            onValueChange = callbacks::onPasswordType,
            label = stringResource(Strings.password),
            placeholder = stringResource(Strings.enter_password),
            keyboardType = KeyboardType.Password,
        )

        SpacerHeight(16.dp)
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Strings.login),
            onClick = callbacks::onLoginClick
        )
        Spacer(Modifier.weight(1f))
        SecondaryButton(
            text = stringResource(CoreStrings.signup),
            onClick = callbacks::onSignUpClick
        )


        /*
        SpacerHeight(32.dp)
        Text(
            text = "For testing purposes only",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
        )
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Simulate login as a student",
            onClick = callbacks::onLoginAsStudentClick
        )
        SpacerHeight(8.dp)
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Simulate login as a teacher",
            onClick = callbacks::onLoginAsTeacherClick
        )*/
    }
}
