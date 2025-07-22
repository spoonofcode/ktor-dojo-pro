package com.spoonofcode.dojopro.routes

import com.spoonofcode.dojopro.core.base.routes.crudRoute
import com.spoonofcode.dojopro.core.data.repository.RoomRepository
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.rooms(roomRepository: RoomRepository = get()) {
    crudRoute(
        basePath = "/rooms",
        repository = roomRepository,
    )
}