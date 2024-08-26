package com.spoonofcode.routes

import com.spoonofcode.core.routes.crudRoute
import com.spoonofcode.repository.CoachRepository
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.coaches(coachRepository: CoachRepository = get()) {
    crudRoute(
        basePath = "/coaches",
        repository = coachRepository,
    )
}