package com.spoonofcode.feature.login.login

import com.spoonofcode.core.model.LoginResponse

sealed class LoginResult {
    data class Success(val loginResponse: LoginResponse) : LoginResult()
    object InvalidCredentials : LoginResult()
    object UserNotFound : LoginResult()
}