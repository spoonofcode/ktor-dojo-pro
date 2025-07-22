package com.spoonofcode.dojopro.core.domain

import com.spoonofcode.dojopro.core.base.utils.PasswordUtil
import com.spoonofcode.dojopro.core.data.repository.UserRepository
import com.spoonofcode.dojopro.core.model.LoginResponse
import com.spoonofcode.dojopro.core.network.JwtConfig
import com.spoonofcode.dojopro.feature.login.login.LoginResult

class LoginUseCase(
    private val userRepository: UserRepository,
    private val passwordUtil: PasswordUtil,
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
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
                userId = existingUser.id,
                jwtAccessToken = jwtAccessToken,
                jwtRefreshToken = jwtRefreshToken,
            )
        )
    }
}