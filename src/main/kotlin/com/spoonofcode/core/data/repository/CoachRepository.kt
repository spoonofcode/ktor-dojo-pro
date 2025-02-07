package com.spoonofcode.core.data.repository

import com.spoonofcode.core.base.repository.GenericCrudRepository
import com.spoonofcode.core.model.CoachRequest
import com.spoonofcode.core.model.CoachResponse
import com.spoonofcode.core.model.Coaches

class CoachRepository : GenericCrudRepository<Coaches, CoachRequest, CoachResponse>(
    table = Coaches,
    toResultRow = { request ->
        mapOf(
            Coaches.firstName to request.firstName,
            Coaches.lastName to request.lastName,
        )
    },
    toResponse = { row ->
        CoachResponse(
            id = row[Coaches.id].value,
            firstName = row[Coaches.firstName],
            lastName = row[Coaches.lastName]
        )
    }
)

