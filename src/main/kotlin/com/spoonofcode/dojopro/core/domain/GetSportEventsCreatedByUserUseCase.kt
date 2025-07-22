package com.spoonofcode.dojopro.core.domain

import com.spoonofcode.dojopro.core.data.repository.SportEventRepository
import com.spoonofcode.dojopro.core.model.SportEventResponse

class GetSportEventsCreatedByUserUseCase(
    private val sportEventRepository: SportEventRepository,
) {
    suspend operator fun invoke(creatorUserId: Int): List<SportEventResponse> = sportEventRepository.readByCreatorUserId(creatorUserId = creatorUserId)
}