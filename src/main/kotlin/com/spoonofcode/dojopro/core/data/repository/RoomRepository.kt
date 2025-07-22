package com.spoonofcode.dojopro.core.data.repository

import com.spoonofcode.dojopro.core.base.repository.GenericCrudRepository
import com.spoonofcode.dojopro.core.model.RoomRequest
import com.spoonofcode.dojopro.core.model.RoomResponse
import com.spoonofcode.dojopro.core.model.Rooms

class RoomRepository : GenericCrudRepository<Rooms, RoomRequest, RoomResponse>(
    table = Rooms,
    toResultRow = { request ->
        mapOf(
            Rooms.name to request.name,
        )
    },
    toResponse = { row ->
        RoomResponse(
            id = row[Rooms.id].value,
            name = row[Rooms.name],
        )
    }
)

