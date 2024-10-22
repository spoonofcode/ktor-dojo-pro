package com.spoonofcode.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GoogleAuthTokenRequest(
    val idToken: String
)

@Serializable
data class GoogleAuthTokenResponse(
    val idToken: String
)