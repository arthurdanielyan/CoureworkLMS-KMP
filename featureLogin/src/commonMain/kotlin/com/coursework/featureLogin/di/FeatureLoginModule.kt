package com.coursework.featureLogin.di

import com.coursework.featureLogin.presentation.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureLoginModule = module {
    viewModelOf(::LoginViewModel)
}