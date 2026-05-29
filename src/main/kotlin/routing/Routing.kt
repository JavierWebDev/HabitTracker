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

        post("/habits") {
            try {
                val request = call.receive<CreateHabitRequest>()
                val userId = call.request.header("Authorization") ?: "aaa"
                log.info("Request: $request Authorization: $userId")
                val createdHabit = habitService.createHabit(request, userId)
                call.respond(HttpStatusCode.Created, createdHabit)
            } catch (e: NoSuchElementException) {
                log.info("Error: ${e.message}")
                call.respond(HttpStatusCode.NotFound, e.message ?: "Error not known")
            } catch (e: IllegalArgumentException) {
                log.info("Bad Request: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Bad request")
            }
        }

        delete("/habits/{habitId}") {
            try {
                val habitId = call.parameters["habitId"]
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing habitId")

                habitService.deleteHabit(habitId)
                log.info("Habit: $habitId was successfully deleted")
                call.respond(HttpStatusCode.OK)

            } catch (e: NoSuchElementException) {
                log.info("Error: ${e.message}")
                call.respond(HttpStatusCode.NotFound, "Missing habitId")
            }
        }
    }
}
