package com.spoonofcode

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.spoonofcode.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureDI()
    configureDatabases()
//    configureOAuthGoogleWebClient()
    configureAuthenticationJWT()
    configureRouting()

    val serviceAccountStream = this::class.java.classLoader.getResourceAsStream("service.account.key.json")
    val options = FirebaseOptions
        .builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
        .build()

    FirebaseApp.initializeApp(options)
}
