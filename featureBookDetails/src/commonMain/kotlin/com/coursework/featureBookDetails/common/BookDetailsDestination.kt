package com.coursework.featureBookDetails.common

import com.coursework.corePresentation.Destination
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailsDestination(
    val id: Long,
    val bookTitle: String,
) : Destination