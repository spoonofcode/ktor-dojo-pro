package com.spoonofcode.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginGoogleRequest(
    val googleUserToken: String
)

@Serializable
data class LoginGoogleResponse(
    val jwtToken: String
)