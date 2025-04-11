package com.spoonofcode.feature.sportevent

import com.spoonofcode.core.base.ext.safeRespond
import com.spoonofcode.core.base.ext.withValidBody
import com.spoonofcode.core.base.ext.withValidParameter
import com.spoonofcode.core.base.ext.withValidQueryParameter
import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.SportEventRepository
import com.spoonofcode.core.domain.AddUserToSportEventUseCase
import com.spoonofcode.core.domain.GetSportEventsCreatedByUserUseCase
import com.spoonofcode.core.model.AddUserToSportEventRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.sportEvents(
    getSportEventsCreatedByUserUseCase: GetSportEventsCreatedByUserUseCase = get(),
    addUserToSportEventUseCase: AddUserToSportEventUseCase = get(),
    sportEventRepository: SportEventRepository = get(),
) {
    val basePath = "/sportEvents"
    crudRoute(
        basePath = basePath,
        repository = sportEventRepository,
    )
    route(basePath) {
        get("") {
            call.withValidQueryParameter<Int>(
                paramName = "creatorUserId",
            ) { creatorUserId ->
                call.safeRespond {
                    val sportEventsByCreatorUserId = getSportEventsCreatedByUserUseCase(creatorUserId = creatorUserId)
                    call.respond(HttpStatusCode.OK, sportEventsByCreatorUserId)
                }
            }
        }

        post("/{sportEventId}/users") {
            call.withValidParameter(
                paramName = "sportEventId",
                parser = String::toIntOrNull
            ) { sportEventId ->
                call.withValidBody<AddUserToSportEventRequest> { body ->
                    call.safeRespond {
                        val userId = body.userId
                        addUserToSportEventUseCase(userId, sportEventId)
                        call.respond(
                            HttpStatusCode.Created,
                            "User with id = $userId added to sport event with id = $sportEventId."
                        )
                    }
                }
            }
        }
    }
}