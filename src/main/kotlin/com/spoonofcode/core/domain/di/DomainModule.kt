package com.spoonofcode.core.domain.di

import com.spoonofcode.core.domain.LoginGoogleUseCase
import com.spoonofcode.core.domain.LoginUseCase
import com.spoonofcode.core.domain.RegisterUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::LoginUseCase)
    singleOf(::LoginGoogleUseCase)
    singleOf(::RegisterUseCase)
}