package com.spoonofcode.dojopro.core.model

import kotlinx.serialization.Serializable

@Serializable
data class AddUserToSportEventRequest(
    val userId: Int,
)