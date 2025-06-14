package com.spoonofcode.feature.sendMessage

import com.google.firebase.messaging.FirebaseMessaging
import com.spoonofcode.core.model.SendMessageDto
import com.spoonofcode.core.model.toMessage
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.sendNotification() {
    route("/send") {
        post {
            val body = call.receiveNullable<SendMessageDto>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            FirebaseMessaging.getInstance().send(body.toMessage())

            call.respond(HttpStatusCode.OK)
        }
    }

    route("/broadcast") {
        post {
            val body = call.receiveNullable<SendMessageDto>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            FirebaseMessaging.getInstance().send(body.toMessage())

            call.respond(HttpStatusCode.OK)
        }
    }
}