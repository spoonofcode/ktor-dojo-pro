package com.spoonofcode.feature.user

import com.spoonofcode.core.base.ext.safeRespond
import com.spoonofcode.core.base.ext.withValidBody
import com.spoonofcode.core.base.ext.withValidParameter
import com.spoonofcode.core.base.ext.withValidQueryParameter
import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.core.domain.AddRoleToUserUseCase
import com.spoonofcode.core.domain.GetAllUsersByRoleIdUseCase
import com.spoonofcode.core.domain.GetSportEventsUserParticipatedInUseCase
import com.spoonofcode.core.model.AddRoleToUserRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.users(
    userRepository: UserRepository = get(),
    getAllUsersByRoleIdUseCase: GetAllUsersByRoleIdUseCase = get(),
    getSportEventsUserParticipatedInUseCase: GetSportEventsUserParticipatedInUseCase = get(),
    addRoleToUserUseCase: AddRoleToUserUseCase = get(),
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
                    val sportEventsByCreatorUserId = getAllUsersByRoleIdUseCase(roleId = roleId)
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

        post("/{userId}/roles") {
            call.withValidParameter(
                paramName = "userId",
                parser = String::toIntOrNull
            ) { userId ->
                call.withValidBody<AddRoleToUserRequest> { body ->
                    call.safeRespond {
                        val roleId = body.roleId
                        addRoleToUserUseCase(roleId, userId)
                        call.respond(
                            HttpStatusCode.Created,
                            "Role with id = $roleId added to user with id = $userId."
                        )
                    }
                }
            }
        }
    }
}