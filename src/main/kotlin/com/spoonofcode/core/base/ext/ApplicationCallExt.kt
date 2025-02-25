package com.spoonofcode.core.base.ext

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend inline fun ApplicationCall.safeRespond(block: suspend () -> Unit) {
    try {
        block()
    } catch (e: Throwable) {
        // TODO Change later with general error message or specify mapping for exceptions with messages
//        respond(HttpStatusCode.InternalServerError, "An error occurred while processing your request.")
        respond(HttpStatusCode.InternalServerError, e.message ?: e.stackTraceToString())
    }
}

suspend fun ApplicationCall.withValidId(
    onInvalid: suspend () -> Unit = {
        respond(HttpStatusCode.BadRequest, "Missing or invalid 'id' parameter.")
    },
    block: suspend (Int) -> Unit
) {
    val itemId = parameters["id"]?.toIntOrNull()
    if (itemId == null) {
        onInvalid()
    } else {
        block(itemId)
    }
}
