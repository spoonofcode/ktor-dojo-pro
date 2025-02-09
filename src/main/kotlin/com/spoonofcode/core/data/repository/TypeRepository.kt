package com.spoonofcode.core.data.repository

import com.spoonofcode.core.base.repository.GenericCrudRepository
import com.spoonofcode.core.model.TypeRequest
import com.spoonofcode.core.model.TypeResponse
import com.spoonofcode.core.model.Types

class TypeRepository : GenericCrudRepository<Types, TypeRequest, TypeResponse>(
    table = Types,
    toResultRow = { request ->
        mapOf(
            Types.name to request.name,
        )
    },
    toResponse = { row ->
        TypeResponse(
            id = row[Types.id].value,
            name = row[Types.name],
        )
    }
)

