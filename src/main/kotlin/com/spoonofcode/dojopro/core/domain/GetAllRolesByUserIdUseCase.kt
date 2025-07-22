package com.spoonofcode.dojopro.core.domain

import com.spoonofcode.dojopro.core.data.repository.RoleRepository
import com.spoonofcode.dojopro.core.model.RoleResponse

class GetAllRolesByUserIdUseCase(
    private val roleRepository: RoleRepository,
) {
    suspend operator fun invoke(userId: Int): List<RoleResponse> = roleRepository.readAllRolesByUserId(userId = userId)
}