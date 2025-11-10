package com.coursework.corePresentation.externalActivityLauncher.di

import com.coursework.corePresentation.externalActivityLauncher.ExternalActivityLauncher
import com.coursework.corePresentation.externalActivityLauncher.ExternalActivityLauncherImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual val externalActivityLauncherModule = module {
    factory<ExternalActivityLauncher> {
        ExternalActivityLauncherImpl()
    }
}