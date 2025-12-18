package com.coursework.lms

import com.coursework.corePresentation.externalActivityLauncher.di.externalActivityLauncherModule
import com.coursework.corePresentation.shared.books.di.coreBooksModule
import com.coursework.data.di.dataModule
import com.coursework.domain.di.domainModule
import com.coursework.featureBookDetails.di.featureBookDetailsModule
import com.coursework.featureEditBook.di.featureEditBookModule
import com.coursework.featureFavorites.di.favouritesModule
import com.coursework.featureHome.di.featureHomeModule
import com.coursework.featureLogin.di.featureLoginModule
import com.coursework.featureMyAddedBooks.di.featureMyAddedBooksModule
import com.coursework.featureSearchBooks.di.featureSearchBooksModule
import com.coursework.featureSignup.di.signupModule
import com.coursework.lms.appRouter.di.appRouterModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            appRouterModule,
            externalActivityLauncherModule,
            *dataModule.toTypedArray(),
            domainModule,
            coreBooksModule,
            featureLoginModule,
            featureHomeModule,
            *featureSearchBooksModule.toTypedArray(),
            featureBookDetailsModule,
            featureEditBookModule,
            featureMyAddedBooksModule,
            favouritesModule,
            signupModule,
        )
    }
}