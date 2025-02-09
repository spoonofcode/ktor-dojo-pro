package com.spoonofcode.core.data.repository

import com.spoonofcode.core.model.CoachResponse
import com.spoonofcode.core.model.Coaches
import com.spoonofcode.core.model.LevelResponse
import com.spoonofcode.core.model.Levels
import com.spoonofcode.core.model.RoomResponse
import com.spoonofcode.core.model.Rooms
import com.spoonofcode.core.model.SportEventRequest
import com.spoonofcode.core.model.SportEventResponse
import com.spoonofcode.core.model.SportEvents
import com.spoonofcode.core.model.TypeResponse
import com.spoonofcode.core.model.Types
import com.spoonofcode.core.model.UserResponse
import com.spoonofcode.core.model.Users
import com.spoonofcode.core.base.repository.GenericCrudRepository
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class SportEventRepository : GenericCrudRepository<SportEvents, SportEventRequest, SportEventResponse>(
    table = SportEvents,
    leftJoinTables = listOf(Coaches, Levels, Rooms, Types, Users),
    toResultRow = { request ->
        mapOf(
            SportEvents.title to request.title,
            SportEvents.description to request.description,
            SportEvents.minNumberOfPeople to request.minNumberOfPeople,
            SportEvents.maxNumberOfPeople to request.maxNumberOfPeople,
            SportEvents.cost to request.cost,
            SportEvents.startDateTime to request.startDateTime,
            SportEvents.endDateTime to request.endDateTime,
            SportEvents.coachId to request.coachId,
            SportEvents.roomId to request.roomId,
            SportEvents.typeId to request.typeId,
            SportEvents.levelId to request.levelId,
            SportEvents.userId to request.userId,
        )
    },
    toResponse = { row ->
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
            user = UserResponse(row[Users.id].value, row[Users.firstName], row[Users.lastName], row[Users.email]),
        )
    }
) {
    fun readByUserId(userId: Int): List<SportEventResponse> {
        return transaction {
            SportEvents.select { SportEvents.userId eq userId }.map { toResponse(it) }
        }
    }
}