package com.coursework.data.downloader

expect class DownloaderImpl : Downloader {
    override fun downloadFile(
        url: String,
        mimeType: String,
        title: String
    ): Long
}