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
    route("/refresh") {
        post("/") {
            val refreshToken = call.request.headers["Authorization"]?.removePrefix("Bearer ")
            if (refreshToken.isNullOrBlank()) {
                call.respondText("No refresh token provided", status = HttpStatusCode.BadRequest)
                return@post
            }

            try {
                val verifier = JwtConfig.getVerifier()
                val decodedJWT = verifier.verify(refreshToken)

                // Check if it's actually a refresh token
                val isRefresh = decodedJWT.getClaim("refresh").asBoolean()
                if (!isRefresh) {
                    call.respondText("Not a refresh token", status = HttpStatusCode.BadRequest)
                    return@post
                }

                val username = decodedJWT.getClaim("username").asString()

                // Generate new tokens
                val newAccessToken = JwtConfig.createAccessToken(username)
                val newRefreshToken = JwtConfig.createRefreshToken(username)

                // Optionally update stored refresh token in DB here...

                call.respond(
                    mapOf(
                        "accessToken" to newAccessToken,
                        "refreshToken" to newRefreshToken
                    )
                )
            } catch (e: Exception) {
                call.respondText("Invalid or expired refresh token", status = HttpStatusCode.Unauthorized)
            }

        }
    }
}
