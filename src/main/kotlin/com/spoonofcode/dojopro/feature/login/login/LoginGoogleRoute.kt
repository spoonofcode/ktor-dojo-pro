package com.spoonofcode.dojopro.feature.login.login

import com.spoonofcode.dojopro.core.base.ext.safeRespond
import com.spoonofcode.dojopro.core.base.ext.withValidBody
import com.spoonofcode.dojopro.core.domain.LoginGoogleUseCase
import com.spoonofcode.dojopro.core.model.LoginGoogleRequest
import io.ktor.http.*
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
