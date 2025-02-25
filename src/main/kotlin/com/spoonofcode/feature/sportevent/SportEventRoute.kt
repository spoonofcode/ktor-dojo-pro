package com.spoonofcode.feature.sportevent

import com.spoonofcode.core.base.ext.safeRespond
import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.SportEventRepository
import com.spoonofcode.core.domain.SportEventUseCase
import com.spoonofcode.core.model.AddUserToSportEventRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.sportEvents(
    sportEventUseCase: SportEventUseCase = get(),
    sportEventRepository: SportEventRepository = get(),
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
                    val sportEventsByCreatorUserId = sportEventUseCase.getSportEventsCreatedByUser(creatorUserId = creatorUserId)
                    call.respond(HttpStatusCode.OK, sportEventsByCreatorUserId)
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid 'creatorUserId' parameter.")
            }
        }

        post("/{sportEventId}/users") {
            val sportEventId = call.parameters["sportEventId"]?.toIntOrNull()
            if (sportEventId != null) {
                val body = call.receiveNullable<AddUserToSportEventRequest>()
                if (body != null) {
                    call.safeRespond {
                        val userId = body.userId
                        sportEventUseCase.addUserToSportEvent(userId, sportEventId)
                        call.respond(
                            HttpStatusCode.Created,
                            "User with id = $userId added to event with id = $sportEventId."
                        )
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Invalid body.")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid 'sportEventId' parameter.")
            }
        }
    }
}