package com.spoonofcode.di

import com.spoonofcode.core.utils.PasswordUtil
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::PasswordUtil)
}