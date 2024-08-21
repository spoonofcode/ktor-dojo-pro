package com.spoonofcode.routes

import com.spoonofcode.core.routes.crudRoute
import com.spoonofcode.repository.RoomRepository
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.rooms(RoomRepository: RoomRepository = get()) {
    crudRoute(
        basePath = "/rooms",
        repository = RoomRepository,
    )
}