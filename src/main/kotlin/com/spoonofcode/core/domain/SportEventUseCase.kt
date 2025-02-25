package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.SportEventRepository
import com.spoonofcode.core.data.repository.SportEventUsersRepository
import com.spoonofcode.core.model.SportEventResponse

class SportEventUseCase(
    private val sportEventRepository: SportEventRepository,
    private val sportEventUsersRepository: SportEventUsersRepository,
) {
    suspend fun getSportEventsCreatedByUser(creatorUserId: Int): List<SportEventResponse> {
        return sportEventRepository.readByCreatorUserId(creatorUserId = creatorUserId)
    }

    suspend fun addUserToSportEvent(userId: Int, sportEventId: Int) {
        sportEventUsersRepository.addUserToSportEvent(userId, sportEventId)
    }
}