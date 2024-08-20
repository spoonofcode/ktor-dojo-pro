package com.spoonofcode.routes

import com.spoonofcode.repository.SportEventRepository
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
        get("/user/{userId}") {
            val userId = call.parameters["userId"]?.toIntOrNull()

            if (userId != null) {
                try {
                    val items = sportEventRepository.readByUserId(userId)
                    if (items.isNotEmpty()) {
                        call.respond(items)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "SportEvents for userId = $userId not found")
                    }
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid userId format")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing 'userId' parameter")
            }
        }
    }
}