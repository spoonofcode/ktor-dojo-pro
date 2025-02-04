package com.spoonofcode.usecase

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.apache.v2.ApacheHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.spoonofcode.data.model.LoginGoogleResponse
import com.spoonofcode.data.model.UserRequest
import com.spoonofcode.repository.UserRepository
import com.spoonofcode.routes.JwtConfig
import com.spoonofcode.routes.LoginGoogleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginGoogleUseCase(
    private val userRepository: UserRepository,
) {

    suspend fun loginUser(googleUserToken: String): LoginGoogleResult {
        // TODO Consider injecting these two
        val transport = ApacheHttpTransport()
        val factory = GsonFactory.getDefaultInstance()

        val verifier = GoogleIdTokenVerifier.Builder(transport, factory)
            .setAudience(listOf(System.getenv("DOJO_PRO_GOOGLE_CLIENT_ID"))) // Server client id from google cloud console
            .build()

        // Verify received token
        val googleIdToken = withContext(Dispatchers.IO) {
            verifier.verify(googleUserToken)
        }

        if (googleIdToken != null) {
            val payload = googleIdToken.payload
            val email = payload.email

            // verify email
            val existingUser = userRepository.readByEmail(email)
            if (existingUser != null) {
                val jwtAccessToken = JwtConfig.createAccessToken(existingUser.id.toString())
                val jwtRefreshToken = JwtConfig.createRefreshToken(existingUser.id.toString())
                return LoginGoogleResult.Success(
                    LoginGoogleResponse(
                        jwtAccessToken = jwtAccessToken,
                        jwtRefreshToken = jwtRefreshToken,
                    )
                )
            } else {
                val firstName = payload["given_name"]?.toString() ?: email.substringBefore("@")
                val lastName = payload["family_name"]?.toString() ?: email.substringBefore("@")
                val pictureUrl = payload["picture"]?.toString()

                val newUser = userRepository.create(
                    UserRequest(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        provider = "google",
                        providerId = googleIdToken.payload.userId
                    )
                )
                val jwtAccessToken = JwtConfig.createAccessToken(newUser.id.toString())
                val jwtRefreshToken = JwtConfig.createRefreshToken(newUser.id.toString())
                return LoginGoogleResult.Success(
                    LoginGoogleResponse(
                        jwtAccessToken = jwtAccessToken,
                        jwtRefreshToken = jwtRefreshToken,
                    )
                )

            }
        }
        return LoginGoogleResult.InvalidCredentials
    }
}