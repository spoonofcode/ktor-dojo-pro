package com.spoonofcode.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val firstName: String,
    val lastName: String,
    val numberOfEventsIParticipatedIn: Long = 0L,
    val numberOfCreatedEvents: Long,
)