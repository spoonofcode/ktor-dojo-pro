package com.spoonofcode.core.model

import org.jetbrains.exposed.dao.id.IntIdTable

object Roles : IntIdTable() {
    val name = varchar("name", 20)
}