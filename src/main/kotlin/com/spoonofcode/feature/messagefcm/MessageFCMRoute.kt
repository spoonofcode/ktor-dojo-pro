package com.spoonofcode.feature.messagefcm

import com.spoonofcode.core.base.ext.safeRespond
import com.spoonofcode.core.base.ext.withValidBody
import com.spoonofcode.core.domain.SendMessageFCMUseCase
import com.spoonofcode.core.model.MessageFCM
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.messageFCM(sendMessageFCMUseCase: SendMessageFCMUseCase = get()) {
    route("/messageFCM") {
        post("/send") {
            call.withValidBody<MessageFCM> { body ->
                call.safeRespond {
                    sendMessageFCMUseCase(body)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}