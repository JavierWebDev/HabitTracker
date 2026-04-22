package com.example.database.repositories

import com.example.model.Habit
import com.example.model.dtos.CreateHabitRequest
import com.example.model.dtos.UpdateHabitRequest

interface HabitRepository {
    fun getAllHabits(): List<Habit>
    fun createHabit(request: CreateHabitRequest, userId: String) : Habit
    fun updateHabit(request: UpdateHabitRequest, habitId: String, userId: String)
    fun deleteHabit(id: String)
}