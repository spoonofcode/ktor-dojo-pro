package com.spoonofcode.routes

import com.spoonofcode.data.model.RegisterResponse

sealed class RegisterResult {
    data class Success(val registerResponse: RegisterResponse) : RegisterResult()
    object UserAlreadyExist : RegisterResult()
    data class UnknownError(val message: String) : RegisterResult()
}