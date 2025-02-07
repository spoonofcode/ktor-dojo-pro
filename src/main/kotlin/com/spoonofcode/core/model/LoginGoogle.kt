package com.spoonofcode.core.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginGoogleRequest(
    val googleUserToken: String
)

@Serializable
data class LoginGoogleResponse(
    val jwtAccessToken: String,
    val jwtRefreshToken: String,
)