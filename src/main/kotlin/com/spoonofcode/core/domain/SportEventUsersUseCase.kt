package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.SportEventUsersRepository
import com.spoonofcode.feature.sporteventusers.SportEventUsersResult

class SportEventUsersUseCase(
    private val sportEventUsersRepository: SportEventUsersRepository,
) {
    suspend fun addUserToSportEvent(userId: Int, sportEventId: Int): SportEventUsersResult {
        sportEventUsersRepository.addUserToSportEvent(userId, sportEventId)
        return SportEventUsersResult.Success
    }
}