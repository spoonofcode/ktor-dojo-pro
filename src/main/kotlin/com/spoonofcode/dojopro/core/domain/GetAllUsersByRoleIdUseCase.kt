package com.spoonofcode.dojopro.core.domain

import com.spoonofcode.dojopro.core.data.repository.UserRepository
import com.spoonofcode.dojopro.core.model.UserResponse

class GetAllUsersByRoleIdUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(roleId: Int): List<UserResponse> = userRepository.readAllUserByRoleId(roleId = roleId)
}