package com.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class HabitState {
    @SerialName("active") ACTIVE,
    @SerialName("finished") FINISHED;

    companion object {
        fun fromSerialName(value: String) : HabitState {
            return when (value) {
                "active" -> ACTIVE
                "finished" -> FINISHED
                else -> throw IllegalArgumentException("Invalid value '$value'")
            }
        }
    }
}