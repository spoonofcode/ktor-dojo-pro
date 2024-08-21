package com.spoonofcode.repository

import com.spoonofcode.data.model.CoachRequest
import com.spoonofcode.data.model.CoachResponse
import com.spoonofcode.data.model.Coaches

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

