package com.spoonofcode.feature.user

import com.spoonofcode.core.model.SportEventResponse

sealed class UserResult {
    data class Success(val sportEvents: List<SportEventResponse>) : UserResult()
    data class UnknownError(val message: String) : UserResult()
}