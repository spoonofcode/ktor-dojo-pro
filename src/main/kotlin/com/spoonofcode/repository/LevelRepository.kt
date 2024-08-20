package com.spoonofcode.repository

import com.spoonofcode.data.model.LevelRequest
import com.spoonofcode.data.model.LevelResponse
import com.spoonofcode.data.model.Levels

class LevelRepository : GenericCrudRepository<Levels, LevelRequest, LevelResponse>(
    Levels,
    { request ->
        mapOf(
            Levels.name to request.name,
        )
    },
    { row ->
        LevelResponse(
            id = row[Levels.id].value,
            name = row[Levels.name],
        )
    }
)

