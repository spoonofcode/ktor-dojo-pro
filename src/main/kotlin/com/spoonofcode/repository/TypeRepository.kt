package com.spoonofcode.repository

import com.spoonofcode.data.model.TypeRequest
import com.spoonofcode.data.model.TypeResponse
import com.spoonofcode.data.model.Types

class TypeRepository : GenericCrudRepository<Types, TypeRequest, TypeResponse>(
    Types,
    { request ->
        mapOf(
            Types.name to request.name,
        )
    },
    { row ->
        TypeResponse(
            id = row[Types.id].value,
            name = row[Types.name],
        )
    }
)

