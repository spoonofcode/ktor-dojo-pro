package com.spoonofcode.feature.sportevent

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
        get("") {
            // Check if the query parameter "creatorUserId" is present.
            val creatorUserIdParam = call.request.queryParameters["creatorUserId"]?.toIntOrNull()

            if (creatorUserIdParam != null) {
                try {
                    val items = sportEventRepository.readByCreatorUserId(creatorUserId = creatorUserIdParam)
                    if (items.isNotEmpty()) {
                        call.respond(items)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "SportEvents for sportEventId = $creatorUserIdParam not found")
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