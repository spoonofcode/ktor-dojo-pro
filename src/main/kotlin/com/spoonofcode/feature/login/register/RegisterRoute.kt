package com.spoonofcode.feature.login.register

import com.spoonofcode.core.model.RegisterRequest
import com.spoonofcode.core.model.UserRequest
import com.spoonofcode.core.domain.RegisterUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.register(registerUseCase: RegisterUseCase = get()) {
    route("/register") {
        post("/") {
            // Receive a body with email and password
            val body = call.receiveNullable<RegisterRequest>() ?: throw BadRequestException("Invalid body")

            when (val result = registerUseCase.registerUser(
                userRequest = UserRequest(
                    firstName = body.firstName,
                    lastName = body.lastName,
                    email = body.email,
                    password = body.password,
                )
            )) {
                is RegisterResult.Success -> {
                    call.respond(HttpStatusCode.OK, result.registerResponse)
                }

                RegisterResult.UserAlreadyExist -> {
                    call.respond(HttpStatusCode.Conflict, "User with this email: ${body.email} already exists.")
                }

                is RegisterResult.UnknownError -> {
                    call.respond(HttpStatusCode.InternalServerError, result.message)
                }
            }
        }
    }
}
