package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.SportEventRepository
import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.core.model.Profile
import com.spoonofcode.feature.profile.ProfileResult

class ProfileUseCase(
    private val userRepository: UserRepository,
    private val sportEventRepository: SportEventRepository,
) {
    suspend fun getProfile(userId: Int): ProfileResult {
        val user = userRepository.read(id = userId) ?: return ProfileResult.UserNotFound
        val numberOfCreatedEvents = sportEventRepository.countByCreatorUserId(userId)

        return ProfileResult.Success(
            Profile(
                firstName = user.firstName,
                lastName = user.lastName,
                numberOfCreatedEvents = numberOfCreatedEvents,
            )
        )

    }
}