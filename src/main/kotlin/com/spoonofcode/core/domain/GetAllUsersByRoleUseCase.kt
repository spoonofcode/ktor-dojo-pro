package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.core.model.Role
import com.spoonofcode.core.model.UserResponse

class GetAllUsersByRoleUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(role: Role): List<UserResponse> = userRepository.readAllUserByRole(role = role)
}