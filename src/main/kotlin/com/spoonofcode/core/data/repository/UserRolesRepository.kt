package com.spoonofcode.core.data.repository

import com.spoonofcode.core.model.SportEventUsers
import com.spoonofcode.core.model.UserRoles
import com.spoonofcode.plugins.dbQuery
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