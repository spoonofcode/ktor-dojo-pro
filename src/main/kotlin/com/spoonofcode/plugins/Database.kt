package com.spoonofcode.plugins

import com.spoonofcode.core.model.*
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
        SchemaUtils.create(
            Users,
            Clubs,
            Coaches,
            Levels,
            Rooms,
            Types,
            SportEvents,
            SportEventUsers
        )
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
        SchemaUtils.drop(
            Users,
            Clubs,
            Coaches,
            Levels,
            Rooms,
            Types,
            SportEvents,
            SportEventUsers
        ) // Add all the tables you want to drop here
    }
}

private fun setExampleData() {
    Users.insert {
        it[firstName] = "Bartosz"
        it[lastName] = "Luczak"
        it[nickName] = "Lycha"
        it[email] = "bartosz.luczak@gmail.com"
        it[password] = "\$2a\$10\$JvONt8faWClBF4Y5D.9uQO8x2DJDDiVw8VcRwBWmB94tP67WQKNtK"
        it[role] = Role.ADMIN
    }

    Users.insert {
        it[firstName] = "Michal"
        it[lastName] = "Staroszczyk"
        it[email] = "michal.staroszczyk@gmail.com"
        it[password] = "\$2a\$10\$zCx5qtCaWxzP/L6hB3pH6u0wMhvev3WAqokQQ8UcmnYDOI6bNEjS."
        it[role] = Role.CLUB_OWNER
    }

    Users.insert {
        it[firstName] = "Artur"
        it[lastName] = "Mackow"
        it[nickName] = "Tatanka"
        it[email] = "artur.mackow@gmail.com"
        it[password] = "\$2a\$10\$0AgsnrhIbbq3e0jWeW.g0.kniIrjjCXWAs81y69hymh.04YJTKmC."
        it[role] = Role.COACH
    }

    Users.insert {
        it[firstName] = "Dawid"
        it[lastName] = "Platek"
        it[nickName] = "Pajak"
        it[email] = "dawid.platek@gmail.com"
        it[password] = "\$2a\$10\$txXhfXroVdyly1WtQFOw5OfHilnZzKp5ZTptQJcErIk5GErnAhh7e"
        it[role] = Role.COACH
    }

    Users.insert {
        it[firstName] = "Jacek"
        it[lastName] = "Bajor"
        it[email] = "jacek.bajor@gmail.com"
        it[password] = "\$2a\$10\$Biyt911mKRL06oNv9M97QeEifQA76lvcWJpyppPT.puI5tQWW97tW"
        it[role] = Role.COACH
    }

    Users.insert {
        it[firstName] = "Kamil"
        it[lastName] = "laszczyk"
        it[email] = "kamil.laszczyk@gmail.com"
        it[password] = "\$2a\$10\$gU0tHDLR4H6bLE9Ra0wfEOJh05.XxoC03c0XRBKK2G4gMU9/Q7tFm"
        it[role] = Role.COACH
    }

    Users.insert {
        it[firstName] = "Piotr"
        it[lastName] = "Jakubowski"
        it[nickName] = "Piter"
        it[email] = "piotr.jakubowski@gmail.com"
        it[password] = "\$2a\$10\$E0lMFgY72H0KxebWd2RqsuJxKwDM7bNT78TUXl3/gIyxRBPHmAYQG"
        it[role] = Role.COACH
    }

    Users.insert {
        it[firstName] = "Wiktoria"
        it[lastName] = "Zakrzewska"
        it[nickName] = "Wika"
        it[email] = "wiktoria.zakrzewska@gmail.com"
        it[password] = "\$2a\$10\$BfQpbNqb2B5lrtrhQmvjCux0H6wcp2uGnbhk3x7zVg.cpz3ojER0W"
        it[role] = Role.COACH
    }

    Users.insert {
        it[firstName] = "Lukasz"
        it[lastName] = "Skrzypnik"
        it[nickName] = "Perun"
        it[email] = "lukasz.skrzypnik@gmail.com"
        it[password] = "\$2a\$10\$A.bhOFmqlC2iTFCg2Eo2VegQflwP03jq884jevIpHuF2V2zWnp6VG"
        it[role] = Role.COACH
    }

    Users.insert {
        it[firstName] = "Jan"
        it[lastName] = "Kowalski"
        it[nickName] = "User1"
        it[email] = "jan.kowalski@gmail.com"
        it[password] = "\$2a\$10\$RSIgzWxRf75Gbkg4Kul3EOtWREQ4SiDin6N0.D48gx8NEJk63K/hq"
        it[role] = Role.USER
    }

    Users.insert {
        it[firstName] = "Adam"
        it[lastName] = "Nowak"
        it[nickName] = "User2"
        it[email] = "adam.nowak@gmail.com"
        it[password] = "\$2a\$10\$mjR7cnFSIob2/8KuxEzyXucqQ5/X330Ie4uVHjTfbP1e8tRcinpF2"
        it[role] = Role.USER
    }

    Users.insert {
        it[firstName] = "Katarzyna"
        it[lastName] = "Solska"
        it[nickName] = "User3"
        it[email] = "katarzyna.solska@gmail.com"
        it[password] = "\$2a\$10\$TbCkwakRuJ9/MsEk0O85Cez5qSXwn2CdANVbcvmu2zIsDnWjiNz3q"
        it[role] = Role.USER
    }

    Clubs.insert {
        it[name] = "Klub Krav Gym Wroclaw"
        it[location] = "Wroclaw"
    }

    Clubs.insert {
        it[name] = "Klub Siechnice"
        it[location] = "Siechnice"
    }

    Clubs.insert {
        it[name] = "Klub Wysoka"
        it[location] = "Wysoka"
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
        it[name] = "Boks"
    }

    Types.insert {
        it[name] = "Grappling"
    }

    Types.insert {
        it[name] = "Mlodziez (10-14 lat)"
    }

    Types.insert {
        it[name] = "Grupa poczatkujaca naborowa"
    }

    Types.insert {
        it[name] = "Grupa Poczatkujaca"
    }

    Types.insert {
        it[name] = "Trening indywidualny"
    }

    Types.insert {
        it[name] = "Trening motoryczny"
    }

    Types.insert {
        it[name] = "Trening otwarty dla wszzystkich"
    }

    Types.insert {
        it[name] = "Wolny Klub - Wolna Mata!"
    }

    Types.insert {
        it[name] = "Grupa Zaawansowana"
    }

    SportEvents.insert {
        it[title] = "Sport Event 1"
        it[description] = "Sport Event 1"
        it[minNumberOfPeople] = 4
        it[maxNumberOfPeople] = 10
        it[cost] = "100 zl"
        it[startDateTime] = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        it[endDateTime] = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        it[clubId] = 1
        it[coachId] = 1
        it[roomId] = 1
        it[typeId] = 1
        it[levelId] = 1
        it[creatorUserId] = 1
    }

    SportEvents.insert {
        it[title] = "Sport Event 2"
        it[description] = "Sport Event 2"
        it[minNumberOfPeople] = 2
        it[maxNumberOfPeople] = 6
        it[cost] = "200 zl"
        it[startDateTime] = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        it[endDateTime] = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        it[clubId] = 2
        it[coachId] = 2
        it[roomId] = 2
        it[typeId] = 2
        it[levelId] = 2
        it[creatorUserId] = 1
    }

    SportEvents.insert {
        it[title] = "Sport Event 3"
        it[description] = "Sport Event 3"
        it[minNumberOfPeople] = 4
        it[maxNumberOfPeople] = 8
        it[cost] = "300 zl"
        it[startDateTime] = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        it[endDateTime] = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        it[clubId] = 3
        it[coachId] = 3
        it[roomId] = 3
        it[typeId] = 3
        it[levelId] = 3
        it[creatorUserId] = 1
    }

    SportEventUsers.insert {
        it[sportEventId] = 1
        it[userId] = 1
    }

    SportEventUsers.insert {
        it[sportEventId] = 2
        it[userId] = 1
    }
}

suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }