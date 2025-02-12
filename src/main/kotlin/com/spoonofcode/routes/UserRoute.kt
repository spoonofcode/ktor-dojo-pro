package com.spoonofcode.routes

import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.users(userRepository: UserRepository = get()) {
    val basePath = "/users"
    crudRoute(
        basePath = basePath,
        repository = userRepository
    )
    route(basePath) {
        get("$basePath/{userId}/events") {
            val userId = call.parameters["userId"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Nieprawidłowy userId")

            val sportEvents = userRepository.readSportEventsInWhichTheUserParticipates(userId = userId)
            call.respond(sportEvents)
        }
    }
}