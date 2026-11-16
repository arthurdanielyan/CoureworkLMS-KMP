package com.coursework.corePresentation.viewState

import com.coursework.domain.error.ApiException
import com.coursework.domain.error.NetworkException
import commonResources.server_unreachable_message
import commonResources.unknown_error_message
import commonResources.Res.string as CoreStrings

fun Throwable.toErrorMessage(): StringValue {
    return when (this) {
        is NetworkException -> StringValue.StringResource(CoreStrings.server_unreachable_message)
        is ApiException -> {
            val fieldError = fieldErrors.entries.firstOrNull()?.let { (field, message) -> "$field: $message" }
            (fieldError ?: detail)?.let(StringValue::RawString)
                ?: StringValue.StringResource(CoreStrings.unknown_error_message)
        }
        else -> StringValue.StringResource(CoreStrings.unknown_error_message)
    }
}
