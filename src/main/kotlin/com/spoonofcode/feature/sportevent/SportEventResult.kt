package com.spoonofcode.feature.sportevent

import com.spoonofcode.core.model.SportEventResponse

sealed class SportEventResult {
    data class Success(val sportEvents: List<SportEventResponse>) : SportEventResult()
    data class UnknownError(val message: String) : SportEventResult()
}