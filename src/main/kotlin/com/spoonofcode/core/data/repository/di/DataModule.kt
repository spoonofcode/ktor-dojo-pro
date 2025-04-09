package com.spoonofcode.core.data.repository.di

import com.spoonofcode.core.data.repository.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::ClubRepository)
    singleOf(::CoachRepository)
    singleOf(::LevelRepository)
    singleOf(::RoomRepository)
    singleOf(::SportEventRepository)
    singleOf(::SportEventUsersRepository)
    singleOf(::TypeRepository)
    singleOf(::UserRepository)
}