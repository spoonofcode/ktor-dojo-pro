package com.spoonofcode.dojopro.routes

import com.spoonofcode.dojopro.core.base.routes.crudRoute
import com.spoonofcode.dojopro.core.data.repository.TypeRepository
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.types(typeRepository: TypeRepository = get()) {
    crudRoute(
        basePath = "/types",
        repository = typeRepository,
    )
}