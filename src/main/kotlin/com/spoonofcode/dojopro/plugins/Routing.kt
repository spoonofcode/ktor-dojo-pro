package com.spoonofcode.dojopro.plugins

import com.spoonofcode.dojopro.feature.login.login.login
import com.spoonofcode.dojopro.feature.login.login.loginGoogle
import com.spoonofcode.dojopro.feature.login.refresh.refresh
import com.spoonofcode.dojopro.feature.login.register.register
import com.spoonofcode.dojopro.feature.messagefcm.messageFCM
import com.spoonofcode.dojopro.feature.profile.profile
import com.spoonofcode.dojopro.feature.sportevent.sportEvents
import com.spoonofcode.dojopro.feature.user.users
import com.spoonofcode.dojopro.routes.*
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
        messageFCM()
        authenticate("auth-jwt") {
            users()
            roles()
            clubs()
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
