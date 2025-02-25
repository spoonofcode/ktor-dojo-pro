package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.core.model.SportEventResponse

class UserUseCase(
    private val userRepository: UserRepository,
) {
    suspend fun getSportEventsInWhichUserParticipates(userId: Int): List<SportEventResponse> {
        return userRepository.readSportEventsInWhichUserParticipates(userId = userId)
    }
}