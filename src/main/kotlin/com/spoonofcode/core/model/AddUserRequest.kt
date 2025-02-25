package com.spoonofcode.core.model

import kotlinx.serialization.Serializable

@Serializable
data class AddUserRequest(
    val userId: Int,
)