package com.example.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable


@Serializable
class Habit (
    var id: String,
    // TODO investigar
    val userId : String,
    var name: String,
    var frequency: List<DaysOfWeek>,
    var startDate: LocalDate,
    var endDate: LocalDate,
    var state: HabitState,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    )