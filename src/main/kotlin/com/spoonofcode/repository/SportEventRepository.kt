package com.spoonofcode.repository

import com.spoonofcode.data.model.SportEventRequest
import com.spoonofcode.data.model.SportEventResponse
import com.spoonofcode.data.model.SportEvents
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class SportEventRepository : GenericCrudRepository<SportEvents, SportEventRequest, SportEventResponse>(
    SportEvents,
    { request ->
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
    { row ->
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
            coachId = row[SportEvents.coachId].value,
            roomId = row[SportEvents.roomId].value,
            typeId = row[SportEvents.typeId].value,
            levelId = row[SportEvents.levelId].value,
            userId = row[SportEvents.userId].value,
        )
    }
) {
    fun readByUserId(userId: Int): List<SportEventResponse> {
        return transaction {
            SportEvents.select { SportEvents.userId eq userId }.map { toResponse(it) }
        }
    }
}