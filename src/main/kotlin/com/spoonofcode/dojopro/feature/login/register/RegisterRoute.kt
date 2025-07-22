package com.spoonofcode.dojopro.feature.login.register

import com.spoonofcode.dojopro.core.base.ext.safeRespond
import com.spoonofcode.dojopro.core.base.ext.withValidBody
import com.spoonofcode.dojopro.core.domain.RegisterUseCase
import com.spoonofcode.dojopro.core.model.RegisterRequest
import com.spoonofcode.dojopro.core.model.UserRequest
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.register(registerUseCase: RegisterUseCase = get()) {
    route("/register") {
        post("/") {
            call.withValidBody<RegisterRequest> { body ->
                call.safeRespond {
                    when (val result = registerUseCase(
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
                    }
                }
            }
        }
    }
}
