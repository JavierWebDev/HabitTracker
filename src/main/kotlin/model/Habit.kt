package com.example.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
class Habit (
    val id: String,
    // TODO investigar
    // val userId: Int,
    val name: String,
    val frequency: List<DaysOfWeek>,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val state: HabitState,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    )