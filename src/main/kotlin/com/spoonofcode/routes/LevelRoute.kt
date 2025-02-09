package com.spoonofcode.routes

import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.LevelRepository
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.levels(levelRepository: LevelRepository = get()) {
    crudRoute(
        basePath = "/levels",
        repository = levelRepository,
    )
}