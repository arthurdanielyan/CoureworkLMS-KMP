package com.coursework.lms.featureRecoverPassword.di

import com.coursework.lms.featureRecoverPassword.createNewPassword.presentation.CreateNewPasswordViewModel
import com.coursework.lms.featureRecoverPassword.verifyCode.presentation.VerifyCodeViewModel
import com.coursework.lms.featureRecoverPassword.verifyPasswordRecovery.presentation.VerifyPasswordRecoveryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val passwordRecoveryModule = module {
    viewModelOf(::VerifyPasswordRecoveryViewModel)
    viewModelOf(::VerifyCodeViewModel)
    viewModelOf(::CreateNewPasswordViewModel)
}