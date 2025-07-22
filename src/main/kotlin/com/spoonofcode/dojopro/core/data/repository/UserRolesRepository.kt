package com.spoonofcode.dojopro.core.data.repository

import com.spoonofcode.dojopro.core.model.SportEventUsers
import com.spoonofcode.dojopro.core.model.UserRoles
import com.spoonofcode.dojopro.plugins.dbQuery
import org.jetbrains.exposed.sql.insert

class UserRolesRepository {
    suspend fun addRoleToUser(roleId: Int, userId: Int) {
        dbQuery {
            UserRoles.insert {
                it[UserRoles.roleId] = roleId
                it[SportEventUsers.userId] = userId
            }
        }
    }
}