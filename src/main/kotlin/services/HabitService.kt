package com.example.services

import com.example.database.repositories.HabitRepository
import com.example.model.Habit
import com.example.model.dtos.CreateHabitRequest

class HabitService(private val repo: HabitRepository) {

    fun createHabit(request: CreateHabitRequest): Habit {
        return repo.createHabit(request)
    }


}