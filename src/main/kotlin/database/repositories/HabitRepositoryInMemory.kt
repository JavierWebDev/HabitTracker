package com.example.database.repositories

import com.example.model.Habit
import com.example.model.HabitState
import com.example.model.dtos.CreateHabitRequest
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID
import kotlinx.datetime.Clock

class HabitRepositoryInMemory : HabitRepository {
    var habits = mutableListOf<Habit>()

    override fun createHabit(request: CreateHabitRequest, userId: String) : Habit {
        val now = Clock.System.now().toLocalDateTime(TimeZone.of("America/Bogota"))

        val habit = Habit(
            id = UUID.randomUUID().toString(),
            userId = userId,
            name = request.name,
            frequency = request.frequency,
            startDate = request.startDate,
            endDate = request.endDate,
            state = HabitState.ACTIVE,
            createdAt = now,
            updatedAt = now
        )

        habits.add(habit)

        return habit
    }

    override fun getAllHabits() : List<Habit> {
        return habits
    }

    override fun updateHabit(habit: Habit) {
        TODO("Not yet implemented")
    }

    override fun deleteHabit(habit: Habit) {
        TODO("Not yet implemented")
    }
}