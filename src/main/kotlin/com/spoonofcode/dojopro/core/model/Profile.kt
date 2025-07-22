package com.spoonofcode.dojopro.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val name: String,
    val numberOfEventsUserParticipatedIn: Long = 0L,
    val numberOfEventsCreatedByUser: Long,
)