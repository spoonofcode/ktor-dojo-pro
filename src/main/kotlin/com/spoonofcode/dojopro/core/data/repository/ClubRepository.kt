package com.spoonofcode.dojopro.core.data.repository

import com.spoonofcode.dojopro.core.base.repository.GenericCrudRepository
import com.spoonofcode.dojopro.core.model.ClubRequest
import com.spoonofcode.dojopro.core.model.ClubResponse
import com.spoonofcode.dojopro.core.model.Clubs

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

