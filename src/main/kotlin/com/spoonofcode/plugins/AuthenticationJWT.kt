package com.spoonofcode.plugins

import com.spoonofcode.routes.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

//fun Application.configureAuthenticationJWT() {
//    install(Authentication) {
//        jwt("auth-jwt") {
//            verifier(JwtConfig.verifier)
//            realm = "ktor sample app"
//            validate { credential ->
//                if (credential.payload.subject != null && credential.payload.getClaim("email").asString() != null) {
//                    JWTPrincipal(credential.payload)
//                } else null
//            }
//        }
//    }
//}

fun Application.configureAuthenticationJWT() {
    install(Authentication) {
        jwt("auth-jwt") {
            verifier(JwtConfig.getVerifier())
            validate { credential ->
                // Check if the token has the correct audience/issuer
                val hasAudience = credential.payload.audience.contains(JwtConfig.audience)
                if (hasAudience && credential.payload.getClaim("userId").asString() != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}