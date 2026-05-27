package com.example.database.repositories

import com.example.model.Habit
import com.example.model.HabitState
import com.example.model.dtos.CreateHabitRequest
import com.example.model.dtos.UpdateHabitRequest
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

    override fun updateHabit(request: UpdateHabitRequest, habitId: String, userId: String) {
        val now = Clock.System.now().toLocalDateTime(TimeZone.of("America/Bogota"))

        for (habit in habits) {
            if (habit.id == habitId) {
                val updatedHabit = Habit(
                    id = habitId,
                    userId = userId,
                    name = request.name,
                    frequency = request.frequency,
                    startDate = request.startDate,
                    endDate = request.endDate,
                    state = HabitState.ACTIVE,
                    createdAt = request.createdAt,
                    updatedAt = now
                )

                habits.remove(habit)

                habits.add(updatedHabit)
            } else {
                throw IllegalArgumentException("Habit not found")
            }
        }
    }

    override fun deleteHabit(habit: Habit) {
        habits.remove(habit)
    }
}