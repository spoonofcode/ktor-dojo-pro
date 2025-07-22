package com.spoonofcode.dojopro.feature.login.register

import com.spoonofcode.dojopro.core.model.RegisterResponse

sealed class RegisterResult {
    data class Success(val registerResponse: RegisterResponse) : RegisterResult()
    object UserAlreadyExist : RegisterResult()
}