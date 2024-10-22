package com.spoonofcode

import com.spoonofcode.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureMonitoring()
    configureSerialization()
    configureDI()
    configureDatabases()
// TODO #3 Remove configureAuthentication
//    configureAuthentication()
    configureAuthenticationJWT()
    configureRouting()
}
