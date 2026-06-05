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

    override fun getAllHabits(userId: String) : List<Habit> {
        val userHabits = mutableListOf<Habit>()

        habits.forEach { habit ->  if (habit.userId == userId) userHabits.add(habit) }

        return userHabits
    }

    override fun updateHabit(request: UpdateHabitRequest, habitId: String, userId: String) {
        val now = Clock.System.now().toLocalDateTime(TimeZone.of("America/Bogota"))

        val habit = habits.find { it.id == habitId }
            ?: throw NoSuchElementException("Habit not found")

        habits.remove(habit)

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

        habits.add(updatedHabit)
    }

    override fun deleteHabit(habitId: String) {

        val habit = habits.find { it.id == habitId }
            ?: throw NoSuchElementException("Habit not found")

        habits.remove(habit)
    }
}