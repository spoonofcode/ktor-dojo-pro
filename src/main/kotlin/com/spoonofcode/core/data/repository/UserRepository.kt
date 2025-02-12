package com.spoonofcode.core.data.repository

import com.spoonofcode.core.base.repository.GenericCrudRepository
import com.spoonofcode.core.model.*
import org.jetbrains.exposed.sql.leftJoin
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository : GenericCrudRepository<Users, UserRequest, UserResponse>(
    table = Users,
    toResultRow = { request ->
        mapOf(
            Users.firstName to request.firstName,
            Users.lastName to request.lastName,
            Users.email to request.email,
            Users.password to request.password,
            Users.provider to request.provider,
            Users.providerId to request.providerId,
        )
    },
    toResponse = { row ->
        UserResponse(
            id = row[Users.id].value,
            firstName = row[Users.firstName],
            lastName = row[Users.lastName],
            email = row[Users.email],
        )
    }
) {
    fun readByEmail(email: String): UserResponse? {
        return transaction {
            Users.select { Users.email eq email }.map { toResponse(it) }
        }.firstOrNull()
    }

    fun readPassword(email: String): String {
        return transaction {
            Users.select { Users.email eq email }.map { it[Users.password] }.firstOrNull() ?: EMPTY_PASSWORD
        }
    }

    fun countSportEventsInWhichTheUserParticipates(userId: Int): Long {
        return transaction {
            (SportEvents innerJoin SportEventUsers)
                .select { SportEventUsers.userId eq userId }
                .count()
        }
    }

    fun readSportEventsInWhichUserParticipates(userId: Int): List<SportEventResponse> {
        return transaction {
            (SportEvents innerJoin SportEventUsers)
                .leftJoin(Coaches)
                .leftJoin(Levels)
                .leftJoin(Rooms)
                .leftJoin(Types)
                .leftJoin(
                    otherTable = Users,
                    onColumn = { SportEvents.creatorUserId },
                    otherColumn = { Users.id }
                )
                .select { SportEventUsers.userId eq userId }
                .map { row ->
                    println("BARTEK row = $row")
                    SportEventResponse(
                        id = row[SportEvents.id].value,
                        creationDate = row[SportEvents.creationDate],
                        updateDate = row[SportEvents.updateDate],
                        title = row[SportEvents.title],
                        description = row[SportEvents.description],
                        minNumberOfPeople = row[SportEvents.minNumberOfPeople],
                        maxNumberOfPeople = row[SportEvents.maxNumberOfPeople],
                        cost = row[SportEvents.cost],
                        startDateTime = row[SportEvents.startDateTime],
                        endDateTime = row[SportEvents.endDateTime],
                        coach = CoachResponse(row[Coaches.id].value, row[Coaches.firstName], row[Coaches.lastName]),
                        room = RoomResponse(row[Rooms.id].value, row[Rooms.name]),
                        type = TypeResponse(row[Types.id].value, row[Types.name]),
                        level = LevelResponse(row[Levels.id].value, row[Levels.name]),
                        creatorUser = UserResponse(
                            row[Users.id].value,
                            row[Users.firstName],
                            row[Users.lastName],
                            row[Users.email]
                        ),
                    )
                }
        }
    }

    companion object {
        private const val EMPTY_PASSWORD = ""
    }
}

