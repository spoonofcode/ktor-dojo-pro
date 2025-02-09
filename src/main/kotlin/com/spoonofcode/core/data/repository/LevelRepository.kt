package com.spoonofcode.core.data.repository

import com.spoonofcode.core.base.repository.GenericCrudRepository
import com.spoonofcode.core.model.LevelRequest
import com.spoonofcode.core.model.LevelResponse
import com.spoonofcode.core.model.Levels

class LevelRepository : GenericCrudRepository<Levels, LevelRequest, LevelResponse>(
    table = Levels,
    toResultRow = { request ->
        mapOf(
            Levels.name to request.name,
        )
    },
    toResponse = { row ->
        LevelResponse(
            id = row[Levels.id].value,
            name = row[Levels.name],
        )
    }
)

