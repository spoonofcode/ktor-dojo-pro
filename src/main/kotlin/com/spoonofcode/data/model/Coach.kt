package com.spoonofcode.data.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class CoachRequest(
    val firstName: String,
    val lastName: String,
)

@Serializable
data class CoachResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
)

object Coaches : IntIdTable() {
    val firstName = varchar("firstName", 128)
    val lastName = varchar("lastName", 128)
}