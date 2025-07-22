package com.spoonofcode.dojopro.routes

import com.spoonofcode.dojopro.core.base.ext.safeRespond
import com.spoonofcode.dojopro.core.base.ext.withValidQueryParameter
import com.spoonofcode.dojopro.core.base.routes.crudRoute
import com.spoonofcode.dojopro.core.data.repository.RoleRepository
import com.spoonofcode.dojopro.core.domain.GetAllRolesByUserIdUseCase
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Route.roles(
    roleRepository: RoleRepository = get(),
    getAllRolesByUserIdUseCase: GetAllRolesByUserIdUseCase = get(),
) {
    val basePath = "/roles"
    crudRoute(
        basePath = basePath,
        repository = roleRepository
    )
    route(basePath) {
        get("") {
            call.withValidQueryParameter<Int>(
                paramName = "userId",
            ) { userId ->
                call.safeRespond {
                    val sportEventsByCreatorRoleId = getAllRolesByUserIdUseCase(userId = userId)
                    call.respond(HttpStatusCode.OK, sportEventsByCreatorRoleId)
                }
            }
        }
    }
}