package com.spoonofcode.core.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class RoomRequest(
    val name: String,
)

@Serializable
data class RoomResponse(
    val id: Int,
    val name: String,
)

object Rooms : IntIdTable() {
    val name = varchar("name", 128)
}