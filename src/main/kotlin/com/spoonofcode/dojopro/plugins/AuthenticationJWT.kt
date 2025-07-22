package com.spoonofcode.dojopro.plugins

import com.spoonofcode.dojopro.core.network.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureAuthenticationJWT() {
    install(Authentication) {
        jwt("auth-jwt") {
            JwtConfig.configureKtorFeature(this)
        }
    }
}