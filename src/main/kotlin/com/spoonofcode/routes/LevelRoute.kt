package com.spoonofcode.routes

import com.spoonofcode.core.routes.crudRoute
import com.spoonofcode.repository.LevelRepository
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.levels(LevelRepository: LevelRepository = get()) {
    crudRoute(
        basePath = "/levels",
        repository = LevelRepository,
    )
}