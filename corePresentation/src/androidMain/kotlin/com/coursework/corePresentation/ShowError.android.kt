package com.coursework.corePresentation

import android.widget.Toast
import coil3.PlatformContext

actual fun showError(message: String, platformContext: PlatformContext) {
    Toast.makeText(platformContext, message, Toast.LENGTH_SHORT).show()
}