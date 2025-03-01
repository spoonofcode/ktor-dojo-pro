package com.spoonofcode.core.domain

import com.spoonofcode.core.data.repository.SportEventRepository
import com.spoonofcode.core.model.SportEventResponse

class GetSportEventsCreatedByUserUseCase(
    private val sportEventRepository: SportEventRepository,
) {
    suspend operator fun invoke(creatorUserId: Int): List<SportEventResponse> = sportEventRepository.readByCreatorUserId(creatorUserId = creatorUserId)
}