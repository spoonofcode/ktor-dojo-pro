package com.spoonofcode.di

import com.spoonofcode.core.data.repository.CoachRepository
import com.spoonofcode.core.data.repository.LevelRepository
import com.spoonofcode.core.data.repository.RoomRepository
import com.spoonofcode.core.data.repository.SportEventRepository
import com.spoonofcode.core.data.repository.TypeRepository
import com.spoonofcode.core.data.repository.UserRepository
import com.spoonofcode.core.domain.LoginUseCase
import com.spoonofcode.core.domain.LoginGoogleUseCase
import com.spoonofcode.core.domain.RegisterUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.spoonofcode.core.utils.PasswordUtil

val appModule = module {
    singleOf(::CoachRepository)
    singleOf(::LevelRepository)
    singleOf(::RoomRepository)
    singleOf(::SportEventRepository)
    singleOf(::TypeRepository)
    singleOf(::UserRepository)

    singleOf(::LoginUseCase)
    singleOf(::LoginGoogleUseCase)
    singleOf(::RegisterUseCase)
    singleOf(::PasswordUtil)
}