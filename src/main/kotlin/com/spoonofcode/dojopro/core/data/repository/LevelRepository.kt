package com.spoonofcode.dojopro.core.data.repository

import com.spoonofcode.dojopro.core.base.repository.GenericCrudRepository
import com.spoonofcode.dojopro.core.model.LevelRequest
import com.spoonofcode.dojopro.core.model.LevelResponse
import com.spoonofcode.dojopro.core.model.Levels

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

