package com.spoonofcode.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlin.text.removePrefix

fun Route.refresh() {
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

                val userId = decodedJWT.getClaim("userId").asString()

                // Generate new tokens
                val newJwtAccessToken = JwtConfig.createAccessToken(userId)
                val newJwtRefreshToken = JwtConfig.createRefreshToken(userId)

                call.respond(
                    mapOf(
                        "jwtAccessToken" to newJwtAccessToken,
                        "jwtRefreshToken" to newJwtRefreshToken
                    )
                )
            } catch (e: Exception) {
                call.respondText("Invalid or expired refresh token", status = HttpStatusCode.Unauthorized)
            }

        }
    }
}