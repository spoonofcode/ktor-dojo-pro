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
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

object JwtConfig {
    private const val secret = "your-secret-key"
    private const val issuer = "com.example"
    private const val validityInMs = 36_000_00 * 24 // 24 hours

    private val algorithm = Algorithm.HMAC256(secret)

    val verifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(userId: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("userId", userId)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}