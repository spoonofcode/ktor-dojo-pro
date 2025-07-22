package com.spoonofcode.dojopro.di

import com.spoonofcode.dojopro.core.base.utils.PasswordUtil
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::PasswordUtil)
}