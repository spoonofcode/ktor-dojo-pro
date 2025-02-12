package com.spoonofcode.core.model

import org.jetbrains.exposed.sql.Table

object SportEventUsers : Table() {
    val sportEventId = reference("sport_event_id", SportEvents.id)
    val userId = reference("user_id", Users.id)
    override val primaryKey = PrimaryKey(sportEventId, userId)
}