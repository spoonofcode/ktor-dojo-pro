package com.spoonofcode.repository

import com.spoonofcode.core.repository.GenericCrudRepository
import com.spoonofcode.data.model.UserRequest
import com.spoonofcode.data.model.UserResponse
import com.spoonofcode.data.model.Users
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository : GenericCrudRepository<Users, UserRequest, UserResponse>(
    table = Users,
    toResultRow = { request ->
        mapOf(
            Users.firstName to request.firstName,
            Users.lastName to request.lastName,
            Users.email to request.email,
        )
    },
    toResponse = { row ->
        UserResponse(
            id = row[Users.id].value,
            firstName = row[Users.firstName],
            lastName = row[Users.lastName],
            email = row[Users.email],
        )
    }
) {
    fun readByEmail(email: String): UserResponse? {
        return transaction {
            Users.select { Users.email eq email }.map { toResponse(it) }
        }.firstOrNull()
    }
}

