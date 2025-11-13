package com.coursework.featureBookDetails.detailsScreen

import androidx.compose.runtime.Immutable

@Immutable
internal interface BookDetailsUiCallbacks {

    fun onBackClick()
    fun onDownloadPdfClick()
    fun onToReviewsClick()
    fun onReserveClick()
    fun onDismissDialog()
    fun onConfirmDate(dateMillis: Long)
    fun onRefresh()
}