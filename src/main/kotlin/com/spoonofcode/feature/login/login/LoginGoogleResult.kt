package com.spoonofcode.feature.login.login

import com.spoonofcode.core.model.LoginGoogleResponse

sealed class LoginGoogleResult {
    data class Success(val loginGoogleResponse: LoginGoogleResponse) : LoginGoogleResult()
    object InvalidCredentials : LoginGoogleResult()
    data class UnknownError(val message: String) : LoginGoogleResult()
}