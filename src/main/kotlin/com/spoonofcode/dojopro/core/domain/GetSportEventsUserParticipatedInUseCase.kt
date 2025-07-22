package com.spoonofcode.dojopro.core.domain

import com.spoonofcode.dojopro.core.data.repository.UserRepository
import com.spoonofcode.dojopro.core.model.SportEventResponse

class GetSportEventsUserParticipatedInUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(userId: Int): List<SportEventResponse> {
        return userRepository.readSportEventsInWhichUserParticipates(userId = userId)
    }
}