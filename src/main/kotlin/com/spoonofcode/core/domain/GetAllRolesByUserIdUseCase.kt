package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.RoleRepository
import com.spoonofcode.core.model.RoleResponse

class GetAllRolesByUserIdUseCase(
    private val roleRepository: RoleRepository,
) {
    suspend operator fun invoke(userId: Int): List<RoleResponse> = roleRepository.readAllRolesByUserId(userId = userId)
}