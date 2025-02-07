package com.spoonofcode.routes

import com.spoonofcode.data.model.LoginRequest
import com.spoonofcode.usecase.LoginUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.login(loginUsecase: LoginUseCase = get()) {
    route("/login") {
        post("/") {
            // Receive a body with email and password
            val body = call.receiveNullable<LoginRequest>() ?: throw BadRequestException("Invalid body")

            when (val result = loginUsecase.loginUser(email = body.email, password = body.password)) {
                is LoginResult.Success -> {
                    call.respond(HttpStatusCode.OK, result.loginResponse)
                }

                LoginResult.InvalidCredentials, LoginResult.UserNotFound -> {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid credentials.")
                }

                is LoginResult.UnknownError -> {
                    call.respond(HttpStatusCode.InternalServerError, result.message)
                }
            }
        }
    }
}
