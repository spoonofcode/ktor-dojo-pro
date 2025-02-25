package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.SportEventUsersRepository

class AddUserToSportEventUseCase(
    private val sportEventUsersRepository: SportEventUsersRepository,
) {
    suspend operator fun invoke(userId: Int, sportEventId: Int) {
        sportEventUsersRepository.addUserToSportEvent(userId, sportEventId)
    }
}