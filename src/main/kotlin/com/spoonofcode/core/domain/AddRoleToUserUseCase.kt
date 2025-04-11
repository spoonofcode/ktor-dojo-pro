package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.UserRolesRepository

class AddRoleToUserUseCase(
    private val userRolesRepository: UserRolesRepository,
) {
    suspend operator fun invoke(roleId: Int, userId: Int) {
        userRolesRepository.addRoleToUser(roleId, userId)
    }
}