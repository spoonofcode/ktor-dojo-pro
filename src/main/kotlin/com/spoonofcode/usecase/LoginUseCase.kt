package com.spoonofcode.usecase

import com.spoonofcode.data.model.LoginResponse
import com.spoonofcode.repository.UserRepository
import com.spoonofcode.routes.JwtConfig
import com.spoonofcode.routes.LoginResult
import com.spoonofcode.utils.PasswordUtil

class LoginUseCase(
    private val userRepository: UserRepository,
    private val passwordUtil: PasswordUtil,
) {
    fun loginUser(email: String, password: String): LoginResult {
        // verify email
        val existingUser = userRepository.readByEmail(email) ?: return LoginResult.UserNotFound

        // Verify password
        val hashedPassword = userRepository.readPassword(email)
        if (!passwordUtil.verifyPassword(password, hashedPassword)) {
            return LoginResult.InvalidCredentials
        }

        val jwtAccessToken = JwtConfig.createAccessToken(existingUser.id.toString())
        val jwtRefreshToken = JwtConfig.createRefreshToken(existingUser.id.toString())

        return LoginResult.Success(
            LoginResponse(
                jwtAccessToken = jwtAccessToken,
                jwtRefreshToken = jwtRefreshToken,
            )
        )
    }
}