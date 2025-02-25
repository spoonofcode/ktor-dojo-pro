package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.feature.user.UserResult

class UserUseCase(
    private val userRepository: UserRepository,
) {
    suspend fun getSportEventsInWhichUserParticipates(userId: Int): UserResult {
        val sportEvents = userRepository.readSportEventsInWhichUserParticipates(userId = userId)

        return UserResult.Success(
            sportEvents = sportEvents,
        )
    }
}