package com.spoonofcode.routes

import com.spoonofcode.data.model.RegisterRequest
import com.spoonofcode.data.model.UserRequest
import com.spoonofcode.usecase.RegisterUseCase
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
                    call.respond(HttpStatusCode.OK, result.registerResponse.idToken)
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
