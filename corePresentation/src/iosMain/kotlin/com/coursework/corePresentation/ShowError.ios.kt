package com.coursework.corePresentation

import coil3.PlatformContext
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSTimer
import platform.Foundation.stringWithFormat
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

actual fun showError(message: String, platformContext: PlatformContext) {
    showIosToast(message)
}

fun showIosToast(message: String) {
    val vc = UIApplication.sharedApplication.keyWindow?.rootViewController ?: return
    showToast(vc, message, duration = 2.0)
}


private fun showToast(viewController: UIViewController, message: String, duration: Double = 2.0) {
    // Create alert with no title
    val alert = UIAlertController.alertControllerWithTitle(
        title = null,
        message = message,
        preferredStyle = UIAlertControllerStyleAlert
    )

    // Present the alert
    viewController.presentViewController(alert, animated = true, completion = null)

    // Automatically dismiss after `duration` seconds
    NSTimer.scheduledTimerWithTimeInterval(
        interval = duration,
        repeats = false
    ) { _ ->
        alert.dismissViewControllerAnimated(true, completion = null)
    }
}
