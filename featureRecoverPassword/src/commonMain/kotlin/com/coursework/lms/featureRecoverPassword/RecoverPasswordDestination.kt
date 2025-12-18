package com.coursework.lms.featureRecoverPassword

import com.coursework.corePresentation.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
data class RecoverPasswordDestination(
    val email: String
) : Destination