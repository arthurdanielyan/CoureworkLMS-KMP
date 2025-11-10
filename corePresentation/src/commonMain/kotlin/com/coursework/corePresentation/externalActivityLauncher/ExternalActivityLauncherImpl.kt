package com.coursework.corePresentation.externalActivityLauncher

expect class ExternalActivityLauncherImpl : ExternalActivityLauncher {
    override fun openLocalPdf(filePath: String): Int
    override fun openUrl(url: String, onCompletion: (Int) -> Unit)
}