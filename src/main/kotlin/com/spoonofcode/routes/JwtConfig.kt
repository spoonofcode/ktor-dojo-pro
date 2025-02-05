package com.spoonofcode.routes

//import com.auth0.jwt.JWT
//import com.auth0.jwt.algorithms.Algorithm
//import com.auth0.jwt.exceptions.JWTVerificationException
//import com.auth0.jwt.interfaces.DecodedJWT
//import java.util.Date
//
//object JwtConfig {
//    // In production, store these in config/env variables, not hard-coded
//    private const val secret = "your-secret-key" // Replace with your actual secret
//    private const val issuer = "com.spoonofcode"
//    private const val expirationTimeMs = 36_000_00 * 24 // 24 hours
//
//    private val algorithm = Algorithm.HMAC256(secret)
//    private val expirationDate = Date(System.currentTimeMillis() + expirationTimeMs)
//
//    val verifier = JWT.require(algorithm)
//        .withIssuer(issuer)
//        .build()
//
//    fun generateToken(userId: String, email: String): String = JWT.create()
//        .withIssuer(issuer)
//        .withSubject(userId)
//        .withClaim("email", email)
//        .withExpiresAt(expirationDate)
//        .sign(algorithm)
//
//    fun verifyToken(token: String): DecodedJWT? {
//        return try {
//            val verifier = JWT.require(algorithm)
//                .withIssuer(issuer)
//                .build()
//            verifier.verify(token)
//        } catch (ex: JWTVerificationException) {
//            // Token is invalid or expired
//            null
//        }
//    }
//}

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.jwt.*
import java.util.*

object JwtConfig {
    private const val secret = "your-secret-key"
    private const val issuer = "ktor-dojo-pro-jwt-issuer"
    private const val audience = "ktor-dojo-pro-jwt-audience"
    private const val realm = "ktor-dojo-pro-jwt-realm"

    // Typically 15 minutes for access token (in seconds)
//    private const val accessTokenValidityInMilliSeconds = 15 * 60 * 1000
    private const val accessTokenValidityInMilliSeconds = 36_000_00 * 24 // 24 hours

    // Typically 7 days for refresh token (in seconds)
    private const val refreshTokenValidityInMilliSeconds = 7 * 24 * 60 * 60 * 1000

    private val algorithm = Algorithm.HMAC256(secret)

    fun createAccessToken(userId: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim("userId", userId)
        .withExpiresAt(Date(System.currentTimeMillis() + accessTokenValidityInMilliSeconds))
        .sign(algorithm)

    fun createRefreshToken(userId: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withAudience(audience)
        .withClaim("userId", userId)
        .withClaim("refresh", true)
        .withExpiresAt(Date(System.currentTimeMillis() + refreshTokenValidityInMilliSeconds))
        .sign(algorithm)

    fun getVerifier(): JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .withAudience(audience)
        .build()

    /**
     * Validates JWT credentials. Return `JWTPrincipal` if valid, otherwise `null`.
     */
    fun validateCredential(credential: JWTCredential): JWTPrincipal? {
        // Check your custom claims here if needed
        return if (credential.payload.audience.contains(audience) &&
            credential.payload.getClaim("userId").asString().isNullOrEmpty().not()
        ) {
            JWTPrincipal(credential.payload)
        } else null
    }

    fun configureKtorFeature(config: JWTAuthenticationProvider.Config) {
        with(config) {
            verifier(getVerifier())
            realm = JwtConfig.realm
            validate { credential -> validateCredential(credential) }
        }
    }
}