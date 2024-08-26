package com.spoonofcode.di

import com.spoonofcode.repository.CoachRepository
import com.spoonofcode.repository.LevelRepository
import com.spoonofcode.repository.RoomRepository
import com.spoonofcode.repository.SportEventRepository
import com.spoonofcode.repository.TypeRepository
import com.spoonofcode.repository.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::CoachRepository)
    singleOf(::LevelRepository)
    singleOf(::RoomRepository)
    singleOf(::SportEventRepository)
    singleOf(::TypeRepository)
    singleOf(::UserRepository)
}