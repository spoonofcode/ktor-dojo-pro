package com.spoonofcode.feature.user

import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.core.domain.UserUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.users(
    userRepository: UserRepository = get(),
    userUseCase: UserUseCase = get()
) {
    val basePath = "/users"
    crudRoute(
        basePath = basePath,
        repository = userRepository
    )
    route(basePath) {
        get("/{userId}/sportEvents") {
            val userId = call.parameters["userId"]?.toIntOrNull() ?: throw BadRequestException("Invalid body")

            when (val result = userUseCase.getSportEventsInWhichUserParticipates(userId = userId)) {
                is UserResult.Success -> {
                    call.respond(HttpStatusCode.OK, result.sportEvents)
                }

                is UserResult.UnknownError -> {
                    call.respond(HttpStatusCode.InternalServerError, result.message)
                }
            }

        }
    }
}