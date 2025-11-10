package com.coursework.featureEditBook.presentation

import com.coursework.corePresentation.viewState.StringValue


sealed interface EditBookUiEffect {

    class ShowMessage(
        val message: StringValue,
    ) : EditBookUiEffect
}