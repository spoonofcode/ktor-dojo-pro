package com.spoonofcode.core.data.repository

import com.spoonofcode.core.base.repository.GenericCrudRepository
import com.spoonofcode.core.model.RoleRequest
import com.spoonofcode.core.model.RoleResponse
import com.spoonofcode.core.model.Roles
import com.spoonofcode.core.model.UserRoles
import com.spoonofcode.plugins.dbQuery
import org.jetbrains.exposed.sql.selectAll

class RoleRepository : GenericCrudRepository<Roles, RoleRequest, RoleResponse>(
    table = Roles,
    toResultRow = { request ->
        mapOf(
            Roles.name to request.name,
        )
    },
    toResponse = { row ->
        RoleResponse(
            id = row[Roles.id].value,
            name = row[Roles.name],
        )
    }
) {

    suspend fun readAllRolesByUserId(userId: Int): List<RoleResponse> {
        return dbQuery {
            (Roles innerJoin UserRoles)
                .selectAll().where { UserRoles.userId eq userId }.map(toResponse)
        }
    }
}

