package com.spoonofcode.dojopro.core.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginGoogleRequest(
    val googleUserToken: String
)

@Serializable
data class LoginGoogleResponse(
    val userId: Int,
    val jwtAccessToken: String,
    val jwtRefreshToken: String,
)