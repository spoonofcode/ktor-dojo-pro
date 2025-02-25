package com.spoonofcode.feature.sportevent

import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.SportEventRepository
import com.spoonofcode.core.domain.SportEventUseCase
import com.spoonofcode.core.model.AddUserToSportEventRequest
import com.spoonofcode.feature.sporteventusers.SportEventUsersResult
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.sportEvents(
    sportEventUseCase: SportEventUseCase = get(),
    sportEventRepository: SportEventRepository = get()
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
                when (val result = sportEventUseCase.getSportEventsCreatedByUser(creatorUserId = creatorUserId)) {
                    is SportEventResult.Success -> {
                        call.respond(HttpStatusCode.OK, result.sportEvents)
                    }

                    is SportEventResult.UnknownError -> {
                        call.respond(HttpStatusCode.InternalServerError, result.message)
                    }
                }
            } else {
                throw BadRequestException("Missing 'creatorUserId' parameter.")

            }
        }

        post("/{sportEventId}/users") {
            val sportEventId = call.parameters["sportEventId"]?.toIntOrNull()
            if (sportEventId != null) {
                val addUserToSportEventRequest = call.receive<AddUserToSportEventRequest>()
                val userId = addUserToSportEventRequest.userId

                when (sportEventUseCase.addUserToSportEvent(userId = userId, sportEventId = sportEventId)) {
                    SportEventUsersResult.Success -> {
                        call.respond(HttpStatusCode.Created, "User with id = $userId added to event with id = $sportEventId.")
                    }

                    is SportEventUsersResult.UnknownError -> TODO()
                }
            } else {
                throw BadRequestException("Missing 'sportEventId' parameter.")
            }
        }
    }
}