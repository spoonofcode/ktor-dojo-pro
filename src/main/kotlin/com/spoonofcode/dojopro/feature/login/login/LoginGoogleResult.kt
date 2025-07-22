package com.spoonofcode.dojopro.feature.login.login

import com.spoonofcode.dojopro.core.model.LoginGoogleResponse

sealed class LoginGoogleResult {
    data class Success(val loginGoogleResponse: LoginGoogleResponse) : LoginGoogleResult()
    object InvalidCredentials : LoginGoogleResult()
}