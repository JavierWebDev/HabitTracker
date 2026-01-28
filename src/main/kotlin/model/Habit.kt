package com.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Serializable
class Habit (
    val id: UUID,
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