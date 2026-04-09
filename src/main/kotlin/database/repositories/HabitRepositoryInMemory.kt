package com.example.database.repositories

import com.example.model.Habit
import com.example.model.HabitState
import com.example.model.dtos.CreateHabitRequest
import com.example.model.dtos.UpdateHabitRequest
import java.util.logging.Logger
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.UUID
import kotlinx.datetime.Clock

class HabitRepositoryInMemory : HabitRepository {

    companion object {
        private val log = Logger.getLogger(HabitRepositoryInMemory::class.java.name)
    }

    var habits = mutableListOf<Habit>()

    val now = Clock.System.now().toLocalDateTime(TimeZone.of("America/Bogota"))

    override fun createHabit(request: CreateHabitRequest, userId: String) : Habit {

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

    override fun updateHabit(request: UpdateHabitRequest, userId: String) {
        habits.forEach { habit ->
            if (habit.id == userId) {
                habit.name = request.name
                habit.frequency = request.frequency
                habit.startDate = request.startDate
                habit.endDate = request.endDate
                habit.updatedAt = now

                log.info("Habit updated: $habit")
            } else {
                throw IllegalArgumentException("Habit not found")
            }
         }
    }

    override fun deleteHabit(id: String) {
        TODO("Not yet implemented")
    }
}