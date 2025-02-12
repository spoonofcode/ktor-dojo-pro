package com.spoonofcode.plugins

import com.spoonofcode.feature.login.login.login
import com.spoonofcode.feature.login.login.loginGoogle
import com.spoonofcode.feature.login.refresh.refresh
import com.spoonofcode.feature.login.register.register
import com.spoonofcode.feature.profile.profile
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
            profile()
            // Static plugin. Try to access `/static/index.html`
            static("/static") {
                resources("static")
            }
        }
    }
}
