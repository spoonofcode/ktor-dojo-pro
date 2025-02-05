package com.spoonofcode.plugins

import com.spoonofcode.routes.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        loginGoogle()
        login()
        register()
        refresh()
        authenticate("auth-jwt") {
            users()
            coaches()
            rooms()
            levels()
            types()
            sportEvents()
            // Static plugin. Try to access `/static/index.html`
            static("/static") {
                resources("static")
            }
        }
    }
}
