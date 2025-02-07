package com.spoonofcode.core.data.repository

import com.spoonofcode.core.base.repository.GenericCrudRepository
import com.spoonofcode.core.model.RoomRequest
import com.spoonofcode.core.model.RoomResponse
import com.spoonofcode.core.model.Rooms

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

