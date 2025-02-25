package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.SportEventRepository
import com.spoonofcode.core.data.repository.SportEventUsersRepository
import com.spoonofcode.feature.sportevent.SportEventResult
import com.spoonofcode.feature.sporteventusers.SportEventUsersResult

class SportEventUseCase(
    private val sportEventRepository: SportEventRepository,
    private val sportEventUsersRepository: SportEventUsersRepository,
) {
    suspend fun getSportEventsCreatedByUser(creatorUserId: Int): SportEventResult {
        val eventsCreatedByUser = sportEventRepository.readByCreatorUserId(creatorUserId = creatorUserId)

        return SportEventResult.Success(
            sportEvents = eventsCreatedByUser,
        )
    }

    suspend fun addUserToSportEvent(userId: Int, sportEventId: Int): SportEventUsersResult {
        sportEventUsersRepository.addUserToSportEvent(userId, sportEventId)
        return SportEventUsersResult.Success
    }
}