package com.spoonofcode.plugins

import com.spoonofcode.routes.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
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
