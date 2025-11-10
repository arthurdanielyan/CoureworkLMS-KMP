package com.coursework.corePresentation.viewState

import commonResources.network_error_message
import commonResources.unknown_error_message
import commonResources.Res.string as Strings
import kotlinx.io.IOException

enum class DataLoadingState {
    Loading, Success, Error, NetworkError
}

fun Throwable.toDataLoadingState(): DataLoadingState {
    return when (this) {
        is IOException -> DataLoadingState.NetworkError // TODO: Implement custom network exception
        else -> DataLoadingState.Error
    }
}

fun getErrorMessage(throwable: Throwable): StringValue {
    return when (throwable) {
        is IOException -> StringValue.StringResource(Strings.network_error_message)
        else -> StringValue.StringResource(Strings.unknown_error_message)
    }
}
