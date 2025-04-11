package com.spoonofcode.core.model

import kotlinx.serialization.Serializable

@Serializable
data class AddRoleToUserRequest(
    val roleId: Int,
)