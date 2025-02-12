package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.feature.sportevent.SportEventResult

class SportEventUseCase(
    private val userRepository: UserRepository,
) {
    suspend fun getSportEventsInWhichUserParticipates(userId: Int): SportEventResult {
        val user = userRepository.read(id = userId) ?: return SportEventResult.UserNotFound
        val sportEventsInWhichUserParticipates =
            userRepository.readSportEventsInWhichUserParticipates(userId = user.id)

        return SportEventResult.Success(sportEventsInWhichUserParticipates)
    }
}