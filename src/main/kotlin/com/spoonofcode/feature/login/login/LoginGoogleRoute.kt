package com.spoonofcode.feature.login.login

import com.spoonofcode.core.model.LoginGoogleRequest
import com.spoonofcode.core.domain.LoginGoogleUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.loginGoogle(loginGoogleUseCase: LoginGoogleUseCase = get()) {
    route("/login/google") {
        post("/") {
            // Receive a body with idToken
            val body = call.receiveNullable<LoginGoogleRequest>() ?: throw BadRequestException("Invalid body")

            when (val result = loginGoogleUseCase.loginUser(googleUserToken = body.googleUserToken)) {
                is LoginGoogleResult.Success -> {
                    call.respond(HttpStatusCode.OK, result.loginGoogleResponse)
                }

                LoginGoogleResult.InvalidCredentials -> {
                    call.respond(HttpStatusCode.Unauthorized, "Invalid credentials.")
                }

                is LoginGoogleResult.UnknownError -> {
                    call.respond(HttpStatusCode.InternalServerError, result.message)
                }
            }
        }
    }
}
