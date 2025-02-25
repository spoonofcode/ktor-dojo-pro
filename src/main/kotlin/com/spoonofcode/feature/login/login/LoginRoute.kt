package com.spoonofcode.feature.login.login

import com.spoonofcode.core.base.ext.safeRespond
import com.spoonofcode.core.domain.LoginUseCase
import com.spoonofcode.core.model.LoginRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.login(loginUsecase: LoginUseCase = get()) {
    route("/login") {
        post("/") {
            val body = call.receiveNullable<LoginRequest>()
            if (body != null) {
                call.safeRespond {
                    when (val result = loginUsecase.loginUser(email = body.email, password = body.password)) {
                        is LoginResult.Success -> {
                            call.respond(HttpStatusCode.OK, result.loginResponse)
                        }

                        LoginResult.InvalidCredentials, LoginResult.UserNotFound -> {
                            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials.")
                        }
                    }
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid body.")
            }
        }
    }
}
