package com.example.model.dtos

import com.example.model.DaysOfWeek
import java.time.LocalDate

data class CreateHabitRequest(
    val name: String,
    val frequency: List<DaysOfWeek>,
    val startDate: LocalDate,
    val endDate: LocalDate
)
