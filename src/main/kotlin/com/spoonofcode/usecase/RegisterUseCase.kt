package com.spoonofcode.usecase

import com.spoonofcode.data.model.RegisterResponse
import com.spoonofcode.data.model.UserRequest
import com.spoonofcode.repository.UserRepository
import com.spoonofcode.routes.JwtConfig
import com.spoonofcode.routes.RegisterResult
import com.spoonofcode.utils.PasswordUtil

class RegisterUseCase(
    private val userRepository: UserRepository,
    private val passwordUtil: PasswordUtil,
) {
    suspend fun registerUser(
        userRequest: UserRequest
    ): RegisterResult {
        val existingUser = userRepository.readByEmail(userRequest.email)

        if (existingUser != null) {
            return RegisterResult.UserAlreadyExist
        }

        val hashedPassword = passwordUtil.hashPassword(userRequest.password!!)

        val newUser = userRepository.create(
            userRequest.copy(password = hashedPassword)
        )

        val jwtAccessToken = JwtConfig.createAccessToken(
            userId = newUser.id.toString(),
            email = newUser.email,
        )
        val jwtRefreshToken = JwtConfig.createRefreshToken(
            userId = newUser.id.toString(),
            email = newUser.email,
        )

        return RegisterResult.Success(
            RegisterResponse(
                jwtAccessToken = jwtAccessToken,
                jwtRefreshToken = jwtRefreshToken
            )
        )
    }
}