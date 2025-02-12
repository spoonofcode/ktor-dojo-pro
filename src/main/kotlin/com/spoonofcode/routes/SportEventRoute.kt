package com.spoonofcode.routes

import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.SportEventRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.sportEvents(sportEventRepository: SportEventRepository = get()) {
    val basePath = "/sportEvents"
    crudRoute(
        basePath = basePath,
        repository = sportEventRepository,
    )
    route(basePath) {
        get("$basePath/{sportEventId}") {
            val sportEventId = call.parameters["sportEventId"]?.toIntOrNull()

            if (sportEventId != null) {
                try {
                    val items = sportEventRepository.readByCreatorUserId(sportEventId)
                    if (items.isNotEmpty()) {
                        call.respond(items)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "SportEvents for sportEventId = $sportEventId not found")
                    }
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid sportEventId format")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing 'sportEventId' parameter")
            }
        }
    }
}