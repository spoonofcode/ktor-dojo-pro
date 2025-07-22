package com.spoonofcode.dojopro.feature.profile

import com.spoonofcode.dojopro.core.model.Profile

sealed class ProfileResult {
    data class Success(val profile: Profile) : ProfileResult()
    object UserNotFound : ProfileResult()
}