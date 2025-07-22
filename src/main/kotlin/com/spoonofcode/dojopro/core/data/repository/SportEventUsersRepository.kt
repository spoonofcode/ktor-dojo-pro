package com.spoonofcode.dojopro.core.data.repository

import com.spoonofcode.dojopro.core.model.SportEventUsers
import com.spoonofcode.dojopro.plugins.dbQuery
import org.jetbrains.exposed.sql.insert

class SportEventUsersRepository {
    suspend fun addUserToSportEvent(userId: Int, sportEventId: Int) {
        dbQuery {
            SportEventUsers.insert {
                it[SportEventUsers.userId] = userId
                it[SportEventUsers.sportEventId] = sportEventId
            }
        }
    }
}