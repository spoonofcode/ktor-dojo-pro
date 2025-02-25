package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.SportEventRepository
import com.spoonofcode.core.data.repository.SportEventUsersRepository
import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.feature.sportevent.SportEventResult
import com.spoonofcode.feature.sporteventusers.SportEventUsersResult

class SportEventUseCase(
    private val userRepository: UserRepository,
    private val sportEventRepository: SportEventRepository,
    private val sportEventUsersRepository: SportEventUsersRepository,
) {
    suspend fun getSportEventsCreatedByUser(creatorUserId: Int): SportEventResult {
        val eventsCreatedByUser = sportEventRepository.readByCreatorUserId(creatorUserId = creatorUserId)

        println("BARTEK eventsCreatedByUser = $eventsCreatedByUser")

        if(eventsCreatedByUser == null) {
            return SportEventResult.Success(sportEvents = emptyList())
        }

        return SportEventResult.Success(
            sportEvents = eventsCreatedByUser,
        )
    }

    suspend fun addUserToSportEvent(userId: Int, sportEventId: Int): SportEventUsersResult {
        sportEventUsersRepository.addUserToSportEvent(userId, sportEventId)
        return SportEventUsersResult.Success
    }

//    suspend fun getSportEventsInWhichUserParticipates(userId: Int): SportEventResult {
//        val user = userRepository.read(id = userId) ?: return SportEventResult.UserNotFound
//        val sportEventsInWhichUserParticipates =
//            userRepository.readSportEventsInWhichUserParticipates(userId = user.id)
//
//        return SportEventResult.Success(sportEventsInWhichUserParticipates)
//    }
}