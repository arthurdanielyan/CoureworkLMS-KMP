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
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNUserNotificationCenter
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

actual class DownloaderImpl : Downloader {

    @OptIn(ExperimentalForeignApi::class)
    actual override fun downloadFile(url: String, mimeType: String, title: String): Long {
        val nsUrl = NSURL.URLWithString(url)
        if (nsUrl == null) {
            println("Invalid URL: $url")
            return -1
        }

        val session = NSURLSession.sharedSession

        val downloadTask = session.downloadTaskWithURL(nsUrl) { location, _, error ->
            when {
                error != null -> {
                    println("Download failed: ${error.localizedDescription}")
                    return@downloadTaskWithURL
                }
                location == null -> {
                    println("Download location is null")
                    return@downloadTaskWithURL
                }
            }

            val fileManager = NSFileManager.defaultManager
            val documentsDir = fileManager.URLsForDirectory(
                directory = NSDocumentDirectory,
                inDomains = NSUserDomainMask
            ).firstOrNull() as? NSURL

            if (documentsDir == null) {
                println("Documents directory not found")
                return@downloadTaskWithURL
            }

            val ext = mimeType.substringAfterLast('/', "")
            val sanitizedTitle = sanitizeFileName(title)
            val fileName = if (ext.isNotEmpty()) "$sanitizedTitle.$ext" else sanitizedTitle
            val destination = documentsDir.URLByAppendingPathComponent(fileName)

            try {
                if (fileManager.fileExistsAtPath(destination?.path!!)) {
                    fileManager.removeItemAtURL(destination, null)
                }
                fileManager.moveItemAtURL(location, destination, null)
                println("File saved at: ${destination.path}")
            } catch (e: Throwable) {
                println("Failed to move file: ${e.message}")
                return@downloadTaskWithURL
            }

            // Schedule notification on the main thread
            dispatch_async(dispatch_get_main_queue()) {
                val titleText = getLocalizedString("download_complete_title")
                val bodyText = getLocalizedString("download_complete_body", destination.lastPathComponent.orEmpty())

                val content = UNMutableNotificationContent().apply {
                    setTitle(titleText)
                    setBody(bodyText)
                    setSound(UNNotificationSound.defaultSound)
                }

                val request = UNNotificationRequest.requestWithIdentifier(
                    "downloadComplete-${destination.lastPathComponent}",
                    content,
                    null
                )

                UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(request) { notifError ->
                    if (notifError != null) {
                        println("Notification error: ${notifError.localizedDescription}")
                    } else {
                        println("Notification scheduled")
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
