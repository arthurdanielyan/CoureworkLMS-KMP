package com.coursework.lms

import android.app.Application
import org.koin.android.ext.koin.androidContext

class LMSApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(applicationContext)
        }
    }
}