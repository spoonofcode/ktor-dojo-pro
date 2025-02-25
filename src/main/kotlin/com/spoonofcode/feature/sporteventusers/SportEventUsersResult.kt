package com.spoonofcode.feature.sporteventusers

sealed class SportEventUsersResult {
    object Success : SportEventUsersResult()
    data class UnknownError(val message: String) : SportEventUsersResult()
}