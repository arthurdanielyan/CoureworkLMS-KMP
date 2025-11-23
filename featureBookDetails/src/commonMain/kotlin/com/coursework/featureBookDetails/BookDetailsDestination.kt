package com.coursework.featureBookDetails

import com.coursework.corePresentation.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailsDestination(
    val id: Long,
    val bookTitle: String,
) : Destination