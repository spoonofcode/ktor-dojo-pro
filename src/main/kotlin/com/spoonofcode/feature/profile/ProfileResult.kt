package com.spoonofcode.feature.profile

import com.spoonofcode.core.model.Profile

sealed class ProfileResult {
    data class Success(val profile: Profile) : ProfileResult()
    object UserNotFound : ProfileResult()
    data class UnknownError(val message: String) : ProfileResult()
}