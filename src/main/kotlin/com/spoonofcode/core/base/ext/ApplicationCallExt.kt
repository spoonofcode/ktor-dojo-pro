package com.spoonofcode.core.base.ext

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend fun ApplicationCall.safeRespond(block: suspend () -> Unit) {
    try {
        block()
    } catch (e: Throwable) {
        // TODO Change later with general error message or specify mapping for exceptions with messages
//        respond(HttpStatusCode.InternalServerError, "An error occurred while processing your request.")
        respond(HttpStatusCode.InternalServerError, e.message ?: e.stackTraceToString())
    }
}

suspend fun <T> ApplicationCall.withValidParameter(
    paramName: String,
    parser: (String) -> T?,
    block: suspend (T) -> Unit,
) {
    val parsedValue = parameters[paramName]
        ?.let { parser(it) }
        ?: return respond(HttpStatusCode.BadRequest, "Missing or invalid '$paramName' parameter.")

    block(parsedValue)
}

suspend fun <T> ApplicationCall.withValidQueryParameter(
    paramName: String,
    parser: (String) -> T?,
    block: suspend (T) -> Unit,
) {
    val parsedValue = request.queryParameters[paramName]
        ?.let { parser(it) }
        ?: return respond(HttpStatusCode.BadRequest, "Missing or invalid '$paramName' parameter.")

    block(parsedValue)
}

suspend inline fun <reified T : Any> ApplicationCall.withValidBody(block: suspend (T) -> Unit) {
    val body = receiveNullable<T>()
    if (body == null) {
        respond(HttpStatusCode.BadRequest, "Invalid body.")
    } else {
        block(body)
    }
}


