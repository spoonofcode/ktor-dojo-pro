package com.spoonofcode.dojopro.feature.sportevent

import com.spoonofcode.dojopro.core.base.ext.safeRespond
import com.spoonofcode.dojopro.core.base.ext.withValidBody
import com.spoonofcode.dojopro.core.base.ext.withValidParameter
import com.spoonofcode.dojopro.core.base.ext.withValidQueryParameter
import com.spoonofcode.dojopro.core.base.routes.crudRoute
import com.spoonofcode.dojopro.core.data.repository.SportEventRepository
import com.spoonofcode.dojopro.core.domain.AddUserToSportEventUseCase
import com.spoonofcode.dojopro.core.domain.GetSportEventsCreatedByUserUseCase
import com.spoonofcode.dojopro.core.model.AddUserToSportEventRequest
import io.ktor.http.*
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