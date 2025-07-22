package com.spoonofcode.dojopro.plugins

import com.spoonofcode.dojopro.core.data.repository.di.dataModule
import com.spoonofcode.dojopro.core.domain.di.domainModule
import com.spoonofcode.dojopro.di.appModule
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