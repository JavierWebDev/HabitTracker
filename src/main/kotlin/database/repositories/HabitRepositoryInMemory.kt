package com.example.database.repositories

import com.example.model.Habit
import com.example.model.HabitState
import com.example.model.dtos.CreateHabitRequest
import java.time.LocalDateTime
import java.util.UUID

class HabitRepositoryInMemory : HabitRepository {
    var habits = mutableListOf<Habit>()

    override fun createHabit(request: CreateHabitRequest) {
        val habit = Habit(
            id = UUID.randomUUID(),
            name = request.name,
            frequency = request.frequency,
            startDate = request.startDate,
            endDate = request.endDate,
            state = HabitState.ACTIVE,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        habits.add(habit)

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