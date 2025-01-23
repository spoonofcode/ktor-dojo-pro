package com.spoonofcode.routes

import com.spoonofcode.data.model.LoginResponse

sealed class LoginResult {
    data class Success(val loginResponse: LoginResponse) : LoginResult()
    object InvalidCredentials : LoginResult()
    object UserNotFound : LoginResult()
    data class UnknownError(val message: String) : LoginResult()
}