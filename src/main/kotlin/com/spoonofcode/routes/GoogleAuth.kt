package com.spoonofcode.routes

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.apache.v2.ApacheHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.spoonofcode.data.model.GoogleAuthTokenRequest
import com.spoonofcode.data.model.GoogleAuthTokenResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun Route.googleAuth() {
    route("/google-auth") {
        post("/") {
            // TODO #29 Remove println
            println("BARTEK Execute Route.googleAuth")
            // Receive a body with idToken
            val body = call.receiveNullable<GoogleAuthTokenRequest>() ?: throw BadRequestException("Invalid body")

            // Consider injecting these two
            val transport = ApacheHttpTransport()
            val factory = GsonFactory.getDefaultInstance()

            val verifier = GoogleIdTokenVerifier.Builder(transport, factory)
                .setAudience(listOf(System.getenv("DOJO_PRO_GOOGLE_CLIENT_ID"))) // Server client id from google cloud console
                .build()

            // Verify received token

            val googleIdToken = withContext(Dispatchers.IO) {
                verifier.verify(body.idToken)
            }

            if (googleIdToken != null) {
                val payload = googleIdToken.payload

                // Extract all properties you need to get/create a user
                val email = payload.email
                val name = payload["name"]?.toString() ?: email.substringBefore("@")
                val pictureUrl = payload["picture"]?.toString()

//        val user = userApi.getUserByEmail(email) ?: registerUser(email, name, pictureUrl)
//
//        val tokens = tokenProvider.createTokens(user)

                val token = JwtConfig.generateToken(email)

                call.respond(
                    GoogleAuthTokenResponse(
                        idToken = token
                    )
                )
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid ID Token")
            }


        }
    }
}
