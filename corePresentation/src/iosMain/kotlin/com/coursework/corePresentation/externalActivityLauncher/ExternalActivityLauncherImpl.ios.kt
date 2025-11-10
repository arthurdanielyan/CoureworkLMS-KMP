package com.coursework.corePresentation.externalActivityLauncher

import platform.Foundation.NSDictionary
import platform.Foundation.NSURL
import platform.Foundation.dictionary
import platform.QuickLook.QLPreviewController
import platform.QuickLook.QLPreviewControllerDataSourceProtocol
import platform.QuickLook.QLPreviewItemProtocol
import platform.UIKit.UIApplication
import platform.darwin.NSObject

actual class ExternalActivityLauncherImpl : ExternalActivityLauncher {

    actual override fun openLocalPdf(filePath: String): Int {
        val url = NSURL.fileURLWithPath(filePath)
        val rootController = UIApplication.sharedApplication.keyWindow?.rootViewController
            ?: return ExternalActivityLauncher.ResultCodes.UnknownError

        return try {
            val previewController = QLPreviewController()
            val dataSource = PdfPreviewDataSource(url)
            previewController.dataSource = dataSource

            rootController.presentViewController(
                previewController,
                animated = true,
                completion = null
            )
            ExternalActivityLauncher.ResultCodes.Success
        } catch (_: Throwable) {
            ExternalActivityLauncher.ResultCodes.UnknownError
        }
    }

    actual override fun openUrl(url: String, onCompletion: (Int) -> Unit) {
        val url = NSURL.URLWithString(url)
        if(url == null) {
            onCompletion(ExternalActivityLauncher.ResultCodes.UnknownError)
            return
        }

        val options = NSDictionary.dictionary()

        UIApplication.sharedApplication.openURL(url, options = options) { success ->
            if (success) {
                onCompletion(ExternalActivityLauncher.ResultCodes.Success)
            } else {
                onCompletion(ExternalActivityLauncher.ResultCodes.UnknownError)
            }
        }
    }
}

private class PdfPreviewDataSource(private val fileUrl: NSURL) : NSObject(),
    QLPreviewControllerDataSourceProtocol {

    override fun numberOfPreviewItemsInPreviewController(controller: QLPreviewController): Long = 1L

    override fun previewController(
        controller: QLPreviewController,
        previewItemAtIndex: Long
    ): QLPreviewItemProtocol = PdfPreviewItem(fileUrl)

}

private class PdfPreviewItem(private val url: NSURL) : NSObject(), QLPreviewItemProtocol {
    override fun previewItemURL(): NSURL = url
    override fun previewItemTitle(): String? = url.lastPathComponent
}
