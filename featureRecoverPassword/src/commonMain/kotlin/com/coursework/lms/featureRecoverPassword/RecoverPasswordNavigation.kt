package com.coursework.lms.featureRecoverPassword

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.coursework.lms.featureRecoverPassword.createNewPassword.CreateNewPasswordDestination
import com.coursework.lms.featureRecoverPassword.createNewPassword.ui.CreateNewPasswordScreen
import com.coursework.lms.featureRecoverPassword.verifyCode.VerifyCodeDestination
import com.coursework.lms.featureRecoverPassword.verifyCode.ui.VerifyCodeScreen
import com.coursework.lms.featureRecoverPassword.verifyPasswordRecovery.VerifyPasswordRecoveryDestination
import com.coursework.lms.featureRecoverPassword.verifyPasswordRecovery.ui.VerifyPasswordRecoveryScreen

fun NavGraphBuilder.recoverPasswordNavigation(
    getParentDestination: () -> RecoverPasswordDestination
) {
    navigation<RecoverPasswordDestination>(
        startDestination = VerifyPasswordRecoveryDestination
    ) {
        composable<VerifyPasswordRecoveryDestination> {
            val recoverPasswordDestination = remember {
                getParentDestination()
            }
            VerifyPasswordRecoveryScreen(recoverPasswordDestination)
        }

        composable<VerifyCodeDestination> {
            VerifyCodeScreen()
        }

        composable<CreateNewPasswordDestination> {
            CreateNewPasswordScreen()
        }
    }
}