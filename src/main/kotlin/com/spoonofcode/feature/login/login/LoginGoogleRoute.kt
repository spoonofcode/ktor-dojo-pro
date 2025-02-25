package com.spoonofcode.feature.login.login

import com.spoonofcode.core.base.ext.safeRespond
import com.spoonofcode.core.base.ext.withValidBody
import com.spoonofcode.core.domain.LoginGoogleUseCase
import com.spoonofcode.core.model.LoginGoogleRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.loginGoogle(loginGoogleUseCase: LoginGoogleUseCase = get()) {
    route("/login/google") {
        post("/") {
            call.withValidBody<LoginGoogleRequest> { body ->
                call.safeRespond {
                    when (val result = loginGoogleUseCase(googleUserToken = body.googleUserToken)) {
                        is LoginGoogleResult.Success -> {
                            call.respond(HttpStatusCode.OK, result.loginGoogleResponse)
                        }

                        LoginGoogleResult.InvalidCredentials -> {
                            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials.")
                        }
                    }
                }
            }
        }
    }
}
