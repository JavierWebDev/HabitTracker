package com.example.routing

import com.example.database.repositories.HabitRepositoryInMemory
import com.example.model.dtos.CreateHabitRequest
import com.example.services.HabitService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.header
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val habitRepo = HabitRepositoryInMemory()
    val habitService = HabitService(habitRepo)


    routing {

        get("/") {
            call.respond(HttpStatusCode.OK, "live")
        }

        post("/habits/createHabit") {
            try {
                val request = call.receive<CreateHabitRequest>()
                val userId = call.request.header("Authorization") ?: "aaa"
                println("Request: $request Authorization: $userId")
                val createdHabit = habitService.createHabit(request, userId)
                call.respond(HttpStatusCode.Created, createdHabit)
            } catch (e: Exception) {
                println("Error: ${e.message}")
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Error not known")
            }
        }

        // Testing jeje

//
//        get("/habits/createHabit") {
//            val habitReq = CreateHabitRequest(
//                name = "Test Habit",
//                frequency = listOf(DaysOfWeek.fromSerialName("1")),
//                startDate = LocalDate.parse("2026-02-28"),
//                endDate = LocalDate.parse("2026-03-02"),
//            )
//
//            val testtoken = "asadas"
//
//            val createdHabit = habitService.createHabit(habitReq, testtoken)
//
//            println(createdHabit.userId)
//            println(createdHabit.name)
//            println(createdHabit.startDate.toString())
//            println(createdHabit.endDate.toString())
//            println(createdHabit.state.toString())
//            println(createdHabit.frequency.toString())
//            call.respond(HttpStatusCode.OK, createdHabit)
//        }
    }
}
