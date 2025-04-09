package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.SportEventRepository
import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.core.model.Profile
import com.spoonofcode.core.model.UserResponse
import com.spoonofcode.feature.profile.ProfileResult

class GetProfileUseCase(
    private val userRepository: UserRepository,
    private val sportEventRepository: SportEventRepository,
) {
    suspend operator fun invoke(userId: Int): ProfileResult {
        val user = userRepository.read(id = userId) ?: return ProfileResult.UserNotFound
        val numberOfEventsCreatedByUser = sportEventRepository.countByCreatorUserId(userId)
        val numberOfEventsUserParticipatedIn = userRepository.countSportEventsInWhichTheUserParticipates(userId)
        return ProfileResult.Success(
            Profile(
                name = getFullName(user = user),
                role = user.role,
                numberOfEventsCreatedByUser = numberOfEventsCreatedByUser,
                numberOfEventsUserParticipatedIn = numberOfEventsUserParticipatedIn,
            )
        )
    }

    private fun getFullName(user: UserResponse): String = buildString {
        append("${user.firstName} ${user.lastName}")
        user.nickName?.let { append(" ($it)") }
    }
}