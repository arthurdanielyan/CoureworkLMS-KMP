package com.coursework.lms.appRouter.di

import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.NavControllersHolder
import com.coursework.lms.appRouter.AppRouterImpl
import org.koin.dsl.module

val appRouterModule = module {
    val appRouter = AppRouterImpl()
    single<AppRouter> {
        appRouter
    }
    single<NavControllersHolder> {
        appRouter
    }
}