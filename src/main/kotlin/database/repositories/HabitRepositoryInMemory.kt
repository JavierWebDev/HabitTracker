package com.example.database.repositories

import com.example.model.Habit
import com.example.model.HabitState
import com.example.model.SimpleHashMap
import com.example.model.dtos.CreateHabitRequest
import com.example.model.dtos.UpdateHabitRequest
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID
import kotlinx.datetime.Clock

class HabitRepositoryInMemory : HabitRepository {

    var habits = SimpleHashMap<String, Habit>()

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

        habits.put(habit.id, habit)

        return habit
    }

    override fun getAllHabits() : List<Habit> {
        return habits.values()
    }

    override fun updateHabit(request: UpdateHabitRequest, habitId: String, userId: String) {
        val now = Clock.System.now().toLocalDateTime(TimeZone.of("America/Bogota"))

        val existing = habits.get(habitId)

        if (existing != null) {
            val updatedHabit = Habit(
                id = habitId,
                userId = userId,
                name = request.name,
                frequency = request.frequency,
                startDate = request.startDate,
                endDate = request.endDate,
                state = existing.state,
                createdAt = existing.createdAt,
                updatedAt = now
            )

            habits.put(habitId, updatedHabit)
        } else {
            throw NoSuchElementException("Habit not found: $habitId")
        }
    }

    override fun deleteHabit(id: String) {
        habits.remove(id)
    }
}