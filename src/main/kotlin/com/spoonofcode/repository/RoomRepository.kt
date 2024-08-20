package com.spoonofcode.repository

import com.spoonofcode.data.model.RoomRequest
import com.spoonofcode.data.model.RoomResponse
import com.spoonofcode.data.model.Rooms

class RoomRepository : GenericCrudRepository<Rooms, RoomRequest, RoomResponse>(
    Rooms,
    { request ->
        mapOf(
            Rooms.name to request.name,
        )
    },
    { row ->
        RoomResponse(
            id = row[Rooms.id].value,
            name = row[Rooms.name],
        )
    }
)

