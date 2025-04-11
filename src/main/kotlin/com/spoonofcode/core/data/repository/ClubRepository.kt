package com.spoonofcode.core.data.repository

import com.spoonofcode.core.base.repository.GenericCrudRepository
import com.spoonofcode.core.model.ClubRequest
import com.spoonofcode.core.model.ClubResponse
import com.spoonofcode.core.model.Clubs

class ClubRepository : GenericCrudRepository<Clubs, ClubRequest, ClubResponse>(
    table = Clubs,
    toResultRow = { request ->
        mapOf(
            Clubs.name to request.name,
            Clubs.location to request.location,
        )
    },
    toResponse = { row ->
        ClubResponse(
            id = row[Clubs.id].value,
            name = row[Clubs.name],
            location = row[Clubs.location]
        )
    }
)

