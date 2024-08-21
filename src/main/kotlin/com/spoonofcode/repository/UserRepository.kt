package com.spoonofcode.repository

import com.spoonofcode.data.model.UserRequest
import com.spoonofcode.data.model.UserResponse
import com.spoonofcode.data.model.Users

class UserRepository : GenericCrudRepository<Users, UserRequest, UserResponse>(
    table = Users,
    toResultRow = { request ->
        mapOf(
            Users.firstName to request.firstName,
            Users.lastName to request.lastName,
        )
    },
    toResponse = { row ->
        UserResponse(
            id = row[Users.id].value,
            firstName = row[Users.firstName],
            lastName = row[Users.lastName]
        )
    }
)

