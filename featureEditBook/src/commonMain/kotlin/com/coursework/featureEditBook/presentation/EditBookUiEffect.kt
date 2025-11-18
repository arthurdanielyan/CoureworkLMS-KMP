package com.coursework.featureEditBook.presentation

import com.coursework.corePresentation.viewState.StringValue


internal sealed interface EditBookUiEffect {

    class ShowMessage(
        val message: StringValue,
    ) : EditBookUiEffect
}