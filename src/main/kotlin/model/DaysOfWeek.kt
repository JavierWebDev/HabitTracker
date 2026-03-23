package com.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DaysOfWeek {
    @SerialName("0") SUNDAY,
    @SerialName("1") MONDAY,
    @SerialName("2") TUESDAY,
    @SerialName("3") WEDNESDAY,
    @SerialName("4") THURSDAY,
    @SerialName("5") FRIDAY,
    @SerialName("6") SATURDAY;

    companion object {
        fun fromSerialName(value: String): DaysOfWeek {
            return when (value) {
                "0" -> SUNDAY
                "1" -> MONDAY
                "2" -> TUESDAY
                "3" -> WEDNESDAY
                "4" -> THURSDAY
                "5" -> FRIDAY
                "6" -> SATURDAY
                else -> throw IllegalArgumentException("Invalid value '$value'")
            }
        }
    }
}