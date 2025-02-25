package com.spoonofcode.feature.sportevent

import com.spoonofcode.core.base.ext.safeRespond
import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.SportEventRepository
import com.spoonofcode.core.data.repository.SportEventUsersRepository
import com.spoonofcode.core.model.AddUserToSportEventRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.sportEvents(
    sportEventRepository: SportEventRepository = get(),
    sportEventUsersRepository: SportEventUsersRepository = get(),
) {
    val basePath = "/sportEvents"
    crudRoute(
        basePath = basePath,
        repository = sportEventRepository,
    )
    route(basePath) {
        get("") {
            val creatorUserId = call.request.queryParameters["creatorUserId"]?.toIntOrNull()
            if (creatorUserId != null) {
                call.safeRespond {
                    val sportEventsByCreatorUserId = sportEventRepository.readByCreatorUserId(creatorUserId = creatorUserId)
                    call.respond(HttpStatusCode.OK, sportEventsByCreatorUserId)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid 'creatorUserId' parameter.")
            }
        }

        post("/{sportEventId}/users") {
            val sportEventId = call.parameters["sportEventId"]?.toIntOrNull()
            if (sportEventId != null) {
                val addUserToSportEventRequest = call.receive<AddUserToSportEventRequest>()
                val userId = addUserToSportEventRequest.userId
                sportEventUsersRepository.addUserToSportEvent(userId, sportEventId)
                call.respond(HttpStatusCode.Created, "User with id = $userId added to event with id = $sportEventId.")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid 'sportEventId' parameter.")
            }
        }
    }
}