package com.spoonofcode.routes

import com.spoonofcode.core.base.ext.safeRespond
import com.spoonofcode.core.base.ext.withValidQueryParameter
import com.spoonofcode.core.base.routes.crudRoute
import com.spoonofcode.core.data.repository.RoleRepository
import com.spoonofcode.core.domain.GetAllRolesByUserIdUseCase
import io.ktor.http.*
import io.ktor.server.application.*
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