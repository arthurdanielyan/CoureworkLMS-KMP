package com.coursework.data.downloader

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSURLSession
import platform.Foundation.NSUserDomainMask
import platform.Foundation.downloadTaskWithURL
import platform.Foundation.stringWithFormat
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNUserNotificationCenter
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

actual class DownloaderImpl : Downloader {

    @OptIn(ExperimentalForeignApi::class)
    actual override fun downloadFile(url: String, mimeType: String, title: String): Long {
        val nsUrl = NSURL.URLWithString(url) ?: return -1
        val session = NSURLSession.sharedSession

        val downloadTask = session.downloadTaskWithURL(nsUrl) { location, _, error ->
            if (error != null) {
                println("Download failed: ${error.localizedDescription}")
                return@downloadTaskWithURL
            }

            location?.let { fileUrl ->
                val fileManager = NSFileManager.defaultManager
                val documentsDir = fileManager.URLsForDirectory(
                    directory = NSDocumentDirectory,
                    inDomains = NSUserDomainMask
                ).firstOrNull() as? NSURL

                val ext = mimeType.substringAfterLast('/', "")
                val sanitizedTitle = sanitizeFileName(title)
                val fileName = if (ext.isNotEmpty()) "$sanitizedTitle.$ext" else sanitizedTitle
                val destination = documentsDir?.URLByAppendingPathComponent(fileName)

                if (destination != null) {
                    try {
                        fileManager.removeItemAtURL(destination, null)
                    } catch (_: Throwable) {
                    }
                    fileManager.moveItemAtURL(fileUrl, destination, null)
                    println("Saved file at: ${destination.path}")

                    // Schedule a local notification
                    dispatch_async(dispatch_get_main_queue()) {
                        val title = getLocalizedString("download_complete_title")
                        val body = getLocalizedString("download_complete_body", destination.lastPathComponent.orEmpty())

                        val alert = UIAlertController.alertControllerWithTitle(
                            title,
                            message = body,
                            preferredStyle = UIAlertControllerStyleAlert
                        )
                        alert.addAction(UIAlertAction.actionWithTitle("OK", style = UIAlertActionStyleDefault) { _ -> })

                        val content = UNMutableNotificationContent().apply {
                            setTitle(title)
                            setBody(body)
                            setSound(UNNotificationSound.defaultSound)
                        }

                        val request = UNNotificationRequest.requestWithIdentifier(
                            "downloadComplete-${destination.lastPathComponent}",
                            content,
                            null
                        )

                        UNUserNotificationCenter.currentNotificationCenter()
                            .addNotificationRequest(request) { error ->
                                if (error != null) {
                                    println("Notification error: ${error.localizedDescription}")
                                } else {
                                    println("Notification scheduled (simulator won’t show it)")
                                }
                            }
                    }
                }
            }
        }

        downloadTask.resume()
        return downloadTask.taskIdentifier.toLong()
    }

    private fun sanitizeFileName(name: String): String {
        return name.replace(Regex("[:/]"), "-")
    }

    private fun getLocalizedString(key: String, arg: Any? = null): String {
        val stringTemplate = NSBundle.mainBundle.localizedStringForKey(
            key = key,
            value = key,
            table = "Localizable"
        )
        println("DEBUG: stringTemplate for key=$key -> '$stringTemplate'")
        return if (arg == null) {
            stringTemplate
        } else {
            NSString.stringWithFormat(stringTemplate, arg)
        }
    }
}
