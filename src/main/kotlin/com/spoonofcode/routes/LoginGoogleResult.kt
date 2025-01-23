package com.spoonofcode.routes

import com.spoonofcode.data.model.LoginGoogleResponse

sealed class LoginGoogleResult {
    data class Success(val loginGoogleResponse: LoginGoogleResponse) : LoginGoogleResult()
    object InvalidCredentials : LoginGoogleResult()
    data class UnknownError(val message: String) : LoginGoogleResult()
}