package com.spoonofcode.dojopro.core.data.repository

import com.spoonofcode.dojopro.core.base.repository.GenericCrudRepository
import com.spoonofcode.dojopro.core.model.TypeRequest
import com.spoonofcode.dojopro.core.model.TypeResponse
import com.spoonofcode.dojopro.core.model.Types

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

