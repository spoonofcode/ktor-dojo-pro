package com.spoonofcode.plugins

import com.spoonofcode.data.model.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val driverClass = environment.config.property("storage.driverClassName").getString()
    val jdbcUrl = environment.config.property("storage.jdbcURL").getString()
    val db = Database.connect(provideDataSource(jdbcUrl, driverClass))
    transaction(db) {
        dropTables()
        SchemaUtils.create(Users, Coaches, Levels, Rooms, Types, SportEvents)
        updateSportEventTrigger()
        setExampleData()
    }
}

private fun provideDataSource(url: String, driverClass: String): HikariDataSource {
    val hikariConfig = HikariConfig().apply {
        driverClassName = driverClass
        jdbcUrl = url
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }
    return HikariDataSource(hikariConfig)
}

private fun dropTables() {
    transaction {
        SchemaUtils.drop(Users, Coaches, Levels, Rooms, Types, SportEvents) // Add all the tables you want to drop here
    }
}

private fun setExampleData() {
    Users.insert {
        it[firstName] = "Leo"
        it[lastName] = "Messi"
    }

    Users.insert {
        it[firstName] = "Christiano"
        it[lastName] = "Ronaldo"
    }

    Users.insert {
        it[firstName] = "Frank"
        it[lastName] = "Lampard"
    }

    Coaches.insert {
        it[firstName] = "Jose"
        it[lastName] = "Murinho"
    }

    Coaches.insert {
        it[firstName] = "Pep"
        it[lastName] = "Guardiola"
    }

    Coaches.insert {
        it[firstName] = "Michal"
        it[lastName] = "Probierz"
    }

    Levels.insert {
        it[name] = "Basic"
    }

    Levels.insert {
        it[name] = "Advance"
    }

    Levels.insert {
        it[name] = "Pro"
    }

    Rooms.insert {
        it[name] = "Training room no. 1"
    }

    Rooms.insert {
        it[name] = "Training room no. 2"
    }

    Rooms.insert {
        it[name] = "Training room no. 3"
    }

    Types.insert {
        it[name] = "stand-up battle"
    }

    Types.insert {
        it[name] = "Direct fight"
    }

    Types.insert {
        it[name] = "Kicks"
    }

    SportEvents.insert {
        it[title] = "Sport Event 1"
        it[description] = "Sport Event 1"
        it[minNumberOfPeople] = 4
        it[maxNumberOfPeople] = 10
        it[cost] = "100 zl"
        it[startDateTime] = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        it[endDateTime] = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        it[coachId] = 1
        it[roomId] = 1
        it[typeId] = 1
        it[levelId] = 1
        it[userId] = 1
    }
}

suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }