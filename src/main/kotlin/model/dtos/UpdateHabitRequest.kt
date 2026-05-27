package com.example.model.dtos

import com.example.model.DaysOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UpdateHabitRequest(
    val name: String,
    val frequency: List<DaysOfWeek>,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val createdAt: LocalDateTime
    )
