package com.spoonofcode.routes

import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.TypeRepository
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.types(typeRepository: TypeRepository = get()) {
    crudRoute(
        basePath = "/types",
        repository = typeRepository,
    )
}