package com.coursework.featureSignup.verifyEmailScreen

import com.coursework.corePresentation.navigation.Destination
import kotlinx.serialization.Serializable

@Serializable
data class VerifyEmailDestination(
    val email: String,
) : Destination
