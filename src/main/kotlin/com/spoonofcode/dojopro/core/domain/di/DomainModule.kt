package com.spoonofcode.dojopro.core.domain.di

import com.spoonofcode.dojopro.core.domain.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::LoginUseCase)
    factoryOf(::LoginGoogleUseCase)
    factoryOf(::GetProfileUseCase)
    factoryOf(::RegisterUseCase)
    factoryOf(::GetAllRolesByUserIdUseCase)
    factoryOf(::GetAllUsersByRoleIdUseCase)
    factoryOf(::GetSportEventsUserParticipatedInUseCase)
    factoryOf(::GetSportEventsCreatedByUserUseCase)
    factoryOf(::AddUserToSportEventUseCase)
    factoryOf(::AddRoleToUserUseCase)
    factoryOf(::SendMessageFCMUseCase)
}