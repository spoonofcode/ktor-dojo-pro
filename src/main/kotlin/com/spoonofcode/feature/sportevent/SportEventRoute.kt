package com.spoonofcode.feature.sportevent

import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.SportEventRepository
import com.spoonofcode.core.domain.SportEventUseCase
import com.spoonofcode.core.model.AddUserRequest
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
            val creatorUserIdParam = call.request.queryParameters["creatorUserId"]

            if (creatorUserIdParam.isNullOrBlank()) {
                throw BadRequestException("Missing or empty 'creatorUserId' parameter")
            }

            val creatorUserId = creatorUserIdParam.toIntOrNull()
            if (creatorUserId == null) {
                throw BadRequestException("'creatorUserId' must be an integer")
            }

            when (val result = sportEventUseCase.getSportEventsCreatedByUser(creatorUserId = creatorUserId)) {
                is SportEventResult.Success -> {
                    call.respond(HttpStatusCode.OK, result.sportEvents)
                }

                is SportEventResult.UnknownError -> {
                    call.respond(HttpStatusCode.InternalServerError, result.message)
                }
            }
        }

        post("/{sportEventId}/users") {
            val sportEventId = call.parameters["sportEventId"]?.toIntOrNull()
            if (sportEventId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid sportEventId ID.")
                return@post
            }

            // Receive the request body with user ID
            val addUserRequest = call.receive<AddUserRequest>()
            val userId = addUserRequest.userId

            when (sportEventUseCase.addUserToSportEvent(userId = userId, sportEventId = sportEventId)) {
                SportEventUsersResult.Success -> {
                    call.respond(HttpStatusCode.Created, "User ($userId) added to event ($sportEventId).")
                }

                is SportEventUsersResult.UnknownError -> TODO()
            }
        }
    }
}