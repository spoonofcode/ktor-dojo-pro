package com.spoonofcode.dojopro.feature.login.login

import com.spoonofcode.dojopro.core.base.ext.safeRespond
import com.spoonofcode.dojopro.core.base.ext.withValidBody
import com.spoonofcode.dojopro.core.domain.LoginUseCase
import com.spoonofcode.dojopro.core.model.LoginRequest
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.login(loginUsecase: LoginUseCase = get()) {
    route("/login") {
        post("/") {
            call.withValidBody<LoginRequest> { body ->
                call.safeRespond {
                    when (val result = loginUsecase(email = body.email, password = body.password)) {
                        is LoginResult.Success -> {
                            call.respond(HttpStatusCode.OK, result.loginResponse)
                        }

                        LoginResult.InvalidCredentials, LoginResult.UserNotFound -> {
                            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials.")
                        }
                    }
                }
            }
        }
    }
}
