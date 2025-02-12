package com.spoonofcode.feature.profile

import com.spoonofcode.core.domain.ProfileUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.profile(profileUseCase: ProfileUseCase = get()) {
    route("/profile") {
        get("/{userId}") {
            val userId = call.parameters["userId"]?.toIntOrNull() ?: throw BadRequestException("Invalid body")

            when (val result = profileUseCase.getProfile(userId = userId)) {
                is ProfileResult.Success -> {
                    call.respond(HttpStatusCode.OK, result.profile)
                }

                ProfileResult.UserNotFound -> {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid credentials.")
                }

                is ProfileResult.UnknownError -> {
                    call.respond(HttpStatusCode.InternalServerError, result.message)
                }
            }
        }
    }
}
