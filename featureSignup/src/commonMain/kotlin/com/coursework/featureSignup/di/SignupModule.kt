package com.coursework.featureSignup.di

import com.coursework.featureSignup.createAccountScreen.presentation.CreateAccountViewModel
import com.coursework.featureSignup.verifyEmailScreen.presentation.VerifyEmailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val signupModule = module {
    viewModelOf(::CreateAccountViewModel)

    viewModelOf(::VerifyEmailViewModel)
}