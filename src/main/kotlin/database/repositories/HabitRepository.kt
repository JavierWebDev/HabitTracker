package com.example.database.repositories

import com.example.model.Habit
import com.example.model.dtos.CreateHabitRequest

interface HabitRepository {
    fun getAllHabits(): List<Habit>
    fun createHabit(request: CreateHabitRequest) : Habit
    fun updateHabit(habit: Habit)
    fun deleteHabit(habit: Habit)
}