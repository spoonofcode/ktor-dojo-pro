package com.spoonofcode.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginGoogleRequest(
    val googleIdToken: String
)

@Serializable
data class LoginGoogleResponse(
    val idToken: String
)