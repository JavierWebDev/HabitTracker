package com.example.services

import com.example.database.repositories.HabitRepository
import com.example.model.Habit
import com.example.model.dtos.CreateHabitRequest
import com.example.model.dtos.UpdateHabitRequest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toLocalDateTime

class HabitService(private val repo: HabitRepository) {

    fun createHabit(request: CreateHabitRequest, userId: String): Habit {

        if (userId.isBlank()) {
            throw IllegalArgumentException("User id can't be blank")
        }

        validateHabitRequest(request)

        return repo.createHabit(request, userId)
    }

    fun validateHabitRequest(request: CreateHabitRequest) {
        val daysUntil = request.startDate.periodUntil(request.endDate).days
        val now = Clock.System.now().toLocalDateTime(TimeZone.of("America/Bogota"))

        if (request.endDate <= request.startDate) {
            throw IllegalArgumentException("endDate must be greater than startDate")
        }

        if (daysUntil < 7) {
            throw IllegalArgumentException("an habit cant last less than 7 days")
        }

        if (request.frequency.isEmpty()) {
            throw IllegalArgumentException("frequency cant be empty")
        }

        if (request.name.isBlank()) {
            throw IllegalArgumentException("name cant be empty")
        }

        if (request.startDate.daysUntil(now.date) > 0) {
            throw IllegalArgumentException("start date cant be in the past")
        }
    }

    fun updateHabit(request: UpdateHabitRequest, userId: String): Habit {
        TODO()
    }

}