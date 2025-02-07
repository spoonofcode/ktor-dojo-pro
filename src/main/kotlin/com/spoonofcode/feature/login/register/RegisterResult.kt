package com.spoonofcode.feature.login.register

import com.spoonofcode.core.model.RegisterResponse

sealed class RegisterResult {
    data class Success(val registerResponse: RegisterResponse) : RegisterResult()
    object UserAlreadyExist : RegisterResult()
    data class UnknownError(val message: String) : RegisterResult()
}