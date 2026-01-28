package com.example.routing

import com.example.database.repositories.HabitRepository
import com.example.database.repositories.HabitRepositoryInMemory
import com.example.model.Habit
import com.example.model.dtos.CreateHabitRequest
import com.example.services.HabitService
import io.ktor.client.request.request
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val habitRepo = HabitRepositoryInMemory()
    val habitService = HabitService(habitRepo)


    routing {
        post("/habits/createHabit") {
            val request = call.receive<CreateHabitRequest>()

            habitService.createHabit(request)

        }
    }
}
