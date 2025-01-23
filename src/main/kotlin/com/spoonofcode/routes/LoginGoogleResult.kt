package com.spoonofcode.routes

import com.spoonofcode.data.model.LoginResponse

sealed class LoginGoogleResult {
    data class Success(val loginResponse: LoginResponse) : LoginGoogleResult()
    object InvalidCredentials : LoginGoogleResult()
    data class UnknownError(val message: String) : LoginGoogleResult()
}