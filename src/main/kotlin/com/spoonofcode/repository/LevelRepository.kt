package com.spoonofcode.repository

import com.spoonofcode.core.repository.GenericCrudRepository
import com.spoonofcode.data.model.LevelRequest
import com.spoonofcode.data.model.LevelResponse
import com.spoonofcode.data.model.Levels

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

