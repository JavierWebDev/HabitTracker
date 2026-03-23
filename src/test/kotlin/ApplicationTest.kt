package com.example

import com.example.database.repositories.HabitRepositoryInMemory
import com.example.model.DaysOfWeek
import com.example.model.HabitState
import com.example.model.dtos.CreateHabitRequest
import com.example.services.HabitService
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertNotNull
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    @Test
    @DisplayName("createHabit returns the object with the correct values")
    fun checkHabitCreationResponse() {
        val repo = HabitRepositoryInMemory()
        val habitService = HabitService(repo)

        val habitRequest = CreateHabitRequest(
            name = "test",
            frequency = listOf(DaysOfWeek.fromSerialName("1"), DaysOfWeek.fromSerialName("2")),
            startDate = today,
            endDate = today.plus(DatePeriod(days = 7)),
        )

        val createdHabit = habitService.createHabit(habitRequest, "test")

        assertNotNull(createdHabit.id)
        assertEquals("test", createdHabit.name)
        assertEquals(2, createdHabit.frequency.size)
        assertEquals(HabitState.ACTIVE, createdHabit.state)
        assertEquals(today, createdHabit.startDate)
        assertEquals(today.plus(DatePeriod(days = 7)), createdHabit.endDate)
    }

    @Test
    @DisplayName("Returns exception if endDate is before startDate")
    fun checkStartDatewithEndDate() {
        val repo = HabitRepositoryInMemory()
        val habitService = HabitService(repo)

        val habitRequest = CreateHabitRequest(
            name = "test",
            frequency = listOf(DaysOfWeek.fromSerialName("1")),
            startDate = today,
            endDate = today.minus(DatePeriod(days = 7)),
        )

        assertFailsWith<IllegalArgumentException> { habitService.createHabit(habitRequest, "test") }

    }

    @Test
    @DisplayName("Returns Exception if the habit have a duration less than seven days")
    fun checkHabitDurationLessThanSevenDays() {
        val repo = HabitRepositoryInMemory()
        val habitService = HabitService(repo)

        val habitRequest = CreateHabitRequest(
            name = "test",
            frequency = listOf(DaysOfWeek.fromSerialName("1"), DaysOfWeek.fromSerialName("2")),
            startDate = today,
            endDate = today.plus(DatePeriod(days = 6)),
        )

        assertFailsWith<IllegalArgumentException> { habitService.createHabit(habitRequest, "test") }
    }

    @Test
    @DisplayName("Returns exception if frequency is empty")
    fun checkFrequencyEmpty() {
        val repo = HabitRepositoryInMemory()
        val habitService = HabitService(repo)

        val habitRequest = CreateHabitRequest(
            name = "test",
            frequency = emptyList(),
            startDate = today,
            endDate = today.plus(DatePeriod(days = 7)),
        )

        assertFailsWith<IllegalArgumentException> { habitService.createHabit(habitRequest, "test") }
    }

    @Test
    @DisplayName("Returns exception if the name is empty")
    fun checkNameEmpty() {
        val repo = HabitRepositoryInMemory()
        val habitService = HabitService(repo)

        val habitRequest = CreateHabitRequest(
            name = "",
            frequency = listOf(DaysOfWeek.fromSerialName("1"), DaysOfWeek.fromSerialName("2")),
            startDate = today,
            endDate = today.plus(DatePeriod(days = 7)),
        )

        assertFailsWith<IllegalArgumentException> { habitService.createHabit(habitRequest, "test") }
    }

    @Test
    @DisplayName("Returns exception if startDate is on the past")
    fun checkStartDateOnPast() {
        val repo = HabitRepositoryInMemory()
        val habitService = HabitService(repo)

        val habitRequest = CreateHabitRequest(
            name = "test",
            frequency = listOf(DaysOfWeek.fromSerialName("1"), DaysOfWeek.fromSerialName("2")),
            startDate = today.minus(DatePeriod(days = 1)),
            endDate = today.plus(DatePeriod(days = 7)),
        )

        assertFailsWith<IllegalArgumentException> { habitService.createHabit(habitRequest, "test") }
    }

}
