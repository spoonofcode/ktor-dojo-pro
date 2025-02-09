package com.spoonofcode.plugins

import com.spoonofcode.core.data.repository.di.dataModule
import com.spoonofcode.core.domain.di.domainModule
import com.spoonofcode.di.appModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureDI() {
    install(Koin) {
        modules(
            appModule,
            dataModule,
            domainModule,
        )
    }
}