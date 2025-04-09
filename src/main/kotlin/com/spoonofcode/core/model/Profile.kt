package com.spoonofcode.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val name: String,
    val role: Role,
    val numberOfEventsUserParticipatedIn: Long = 0L,
    val numberOfEventsCreatedByUser: Long,
)