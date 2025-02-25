package com.spoonofcode.core.model

import kotlinx.serialization.Serializable

@Serializable
data class AddUserToSportEventRequest(
    val userId: Int,
)