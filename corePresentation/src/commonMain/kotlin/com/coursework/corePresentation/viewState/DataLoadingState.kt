package com.coursework.corePresentation.viewState

import commonResources.network_error_message
import commonResources.unknown_error_message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.io.IOException
import commonResources.Res.string as Strings

enum class DataLoadingState {
    Loading, Success, Error, NetworkError;

    val isError: Boolean
        get() = this == Error || this == NetworkError
}

fun zipLoadingStates(vararg dataLoadingStates: StateFlow<DataLoadingState>): Flow<DataLoadingState> {
    return combine(*dataLoadingStates) {
        zipLoadingStates(*it)
    }
}

fun zipLoadingStates(vararg dataLoadingStates: DataLoadingState): DataLoadingState {
    return when {
        dataLoadingStates.any { it == DataLoadingState.Loading } -> DataLoadingState.Loading
        dataLoadingStates.any { it.isError } -> DataLoadingState.Error
        dataLoadingStates.all { it == DataLoadingState.NetworkError } -> DataLoadingState.NetworkError
        dataLoadingStates.all { it == DataLoadingState.Success } -> DataLoadingState.Success
        else -> DataLoadingState.Error
    }
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
