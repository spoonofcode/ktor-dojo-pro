package com.spoonofcode.feature.user

import com.spoonofcode.core.base.ext.safeRespond
import com.spoonofcode.core.base.ext.withValidParameter
import com.spoonofcode.core.base.ext.withValidQueryParameter
import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.core.domain.GetAllUsersByRoleUseCase
import com.spoonofcode.core.domain.GetSportEventsUserParticipatedInUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.users(
    userRepository: UserRepository = get(),
    getAllUsersByRoleUseCase: GetAllUsersByRoleUseCase = get(),
    getSportEventsUserParticipatedInUseCase: GetSportEventsUserParticipatedInUseCase = get(),
) {
    val basePath = "/users"
    crudRoute(
        basePath = basePath,
        repository = userRepository
    )
    route(basePath) {
        get("") {
            call.withValidQueryParameter<Int>(
                paramName = "roleId",
            ) { roleId ->
                call.safeRespond {
                    val sportEventsByCreatorUserId = getAllUsersByRoleUseCase(roleId = roleId)
                    call.respond(HttpStatusCode.OK, sportEventsByCreatorUserId)
                }
            }
        }

        get("/{userId}/sportEvents") {
            call.withValidParameter(
                paramName = "userId",
                parser = String::toIntOrNull
            ) { userId ->
                call.safeRespond {
                    val sportEvents = getSportEventsUserParticipatedInUseCase(userId = userId)
                    call.respond(HttpStatusCode.OK, sportEvents)
                }
            }
        }
    }
}