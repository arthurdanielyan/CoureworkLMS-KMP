package com.coursework.data.di

import com.coursework.data.downloader.Downloader
import com.coursework.data.downloader.DownloaderImpl
import org.koin.dsl.module

actual val downloaderModule = module {
    factory<Downloader> {
        DownloaderImpl()
    }
}