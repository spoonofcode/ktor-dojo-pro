package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.core.model.SportEventResponse

class GetSportEventsUserParticipatedInUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(userId: Int): List<SportEventResponse> {
        return userRepository.readSportEventsInWhichUserParticipates(userId = userId)
    }
}