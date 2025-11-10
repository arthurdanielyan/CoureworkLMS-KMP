package com.coursework.corePresentation.externalActivityLauncher

interface ExternalActivityLauncher {

    object ResultCodes {
        const val UnknownError = -1
        const val Success = 0
        const val NoActivityFound = 1
    }

    fun openLocalPdf(filePath: String): Int

    fun openUrl(url: String, onCompletion: (Int) -> Unit = {})
}