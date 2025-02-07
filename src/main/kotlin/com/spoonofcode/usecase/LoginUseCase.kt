package com.spoonofcode.usecase

import com.spoonofcode.data.model.LoginResponse
import com.spoonofcode.repository.UserRepository
import com.spoonofcode.utils.JwtConfig
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

        val jwtAccessToken = JwtConfig.createAccessToken(
            userId = existingUser.id.toString(),
            email = existingUser.email,
        )
        val jwtRefreshToken = JwtConfig.createRefreshToken(
            userId = existingUser.id.toString(),
            email = existingUser.email,
        )

        return LoginResult.Success(
            LoginResponse(
                jwtAccessToken = jwtAccessToken,
                jwtRefreshToken = jwtRefreshToken,
            )
        )
    }
}