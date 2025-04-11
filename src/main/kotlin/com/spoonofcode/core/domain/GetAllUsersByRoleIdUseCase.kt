package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.core.model.UserResponse

class GetAllUsersByRoleIdUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(roleId: Int): List<UserResponse> = userRepository.readAllUserByRoleId(roleId = roleId)
}