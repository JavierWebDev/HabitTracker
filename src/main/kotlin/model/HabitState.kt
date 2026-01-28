package com.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class HabitState {
    @SerialName("active") ACTIVE,
    @SerialName("finished") FINISHED,
}