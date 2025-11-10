package com.coursework.corePresentation.externalActivityLauncher

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

actual class ExternalActivityLauncherImpl(
    private val context: Context
) : ExternalActivityLauncher {

    actual override fun openLocalPdf(filePath: String): Int {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(filePath.toUri(), "application/pdf")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
        }

        try {
            context.startActivity(intent)
            return ExternalActivityLauncher.ResultCodes.Success
        } catch (_: ActivityNotFoundException) {
            return ExternalActivityLauncher.ResultCodes.NoActivityFound
        } catch (_: Throwable) {
            return ExternalActivityLauncher.ResultCodes.UnknownError
        }
    }


    actual override fun openUrl(url: String, onCompletion: (Int) -> Unit) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        try {
            context.startActivity(intent)
            onCompletion(ExternalActivityLauncher.ResultCodes.Success)
        } catch (_: ActivityNotFoundException) {
            onCompletion(ExternalActivityLauncher.ResultCodes.NoActivityFound)
        } catch (_: Throwable) {
            onCompletion(ExternalActivityLauncher.ResultCodes.UnknownError)
        }
    }
}