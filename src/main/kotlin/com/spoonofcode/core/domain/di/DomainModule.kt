package com.spoonofcode.core.domain.di

import com.spoonofcode.core.domain.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::LoginUseCase)
    singleOf(::LoginGoogleUseCase)
    singleOf(::GetProfileUseCase)
    singleOf(::RegisterUseCase)
    singleOf(::GetSportEventsUserParticipatedInUseCase)
    singleOf(::GetSportEventsCreatedByUserUseCase)
    singleOf(::AddUserToSportEventUseCase)
}