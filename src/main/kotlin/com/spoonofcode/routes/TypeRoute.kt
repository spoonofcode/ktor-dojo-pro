package com.spoonofcode.routes

import com.spoonofcode.core.routes.crudRoute
import com.spoonofcode.repository.TypeRepository
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.types(TypeRepository: TypeRepository = get()) {
    crudRoute(
        basePath = "/types",
        repository = TypeRepository,
    )
}