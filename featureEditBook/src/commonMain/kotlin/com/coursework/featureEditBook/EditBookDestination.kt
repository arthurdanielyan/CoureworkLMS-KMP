package com.coursework.featureEditBook

import com.coursework.corePresentation.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
data class EditBookDestination(
    val bookId: Long? = null,
    val bookTitle: String? = null,
    val isNewBook: Boolean = false,
) : Destination