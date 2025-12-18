package com.coursework.featureSignup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.coursework.featureSignup.createAccountScreen.CreateAccountDestination
import com.coursework.featureSignup.createAccountScreen.ui.CreateAccountScreen
import com.coursework.featureSignup.verifyEmailScreen.VerifyEmailDestination
import com.coursework.featureSignup.verifyEmailScreen.ui.VerifyEmailScreen

fun NavGraphBuilder.signupNavigation() {
    navigation<SingupDestination>(
        startDestination = CreateAccountDestination
    ) {
        composable<CreateAccountDestination> {
            CreateAccountScreen()
        }

        composable<VerifyEmailDestination> {
            VerifyEmailScreen()
        }
    }
}