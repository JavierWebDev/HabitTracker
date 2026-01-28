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
    @SerialName("6") SATURDAY,
}