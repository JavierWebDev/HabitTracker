package com.example.routing

import com.example.database.repositories.HabitRepositoryInMemory
import com.example.model.dtos.CreateHabitRequest
import com.example.model.dtos.UpdateHabitRequest
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

        get("/habits") {
            val token = call.request.header("Authorization")
                ?: return@get call.respond(HttpStatusCode.Unauthorized, "Missing authorization")

            val habits = habitService.getHabits(token) //TODO: extract from JWT once implemented
            log.info("Habits: $habits")

            call.respond(HttpStatusCode.OK, habits.toString())
        }

        post("/habits") {
            try {
                val request = call.receive<CreateHabitRequest>()

                val token = call.request.header("Authorization")
                    ?: return@post call.respond(HttpStatusCode.Unauthorized, "Missing authorization")

                log.info("Request: $request Authorization: $token")

                val userId = token // TODO: extract from JWT once implemented

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
                call.request.header("Authorization")
                    ?: return@delete call.respond(HttpStatusCode.Unauthorized, "Missing authorization")

                val habitId = call.parameters["habitId"]
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing habitId")

                habitService.deleteHabit(habitId)
                log.info("Habit: $habitId was successfully deleted")
                call.respond(HttpStatusCode.OK)

            } catch (e: NoSuchElementException) {
                log.error("Habit not found: ${e.message}")
                call.respond(HttpStatusCode.NotFound, "Missing habitId")
            }
        }

        put("habits/{habitId}") {
            try {
                val token = call.request.header("Authorization")
                    ?: return@put call.respond(HttpStatusCode.Unauthorized, "Missing authorization")

                val habitId = call.parameters["habitId"]
                    ?: return@put call.respond(HttpStatusCode.BadRequest, "Missing habitId")

                val request = call.receive<UpdateHabitRequest>()

                habitService.updateHabit(request, habitId, token)
                log.info("Habit $habitId has been succesfully updated")

                call.respond(HttpStatusCode.OK)
            } catch (e: Exception) {
                log.error("${e.printStackTrace()}")
            }
        }

//        post("/auth") {
//
//        }
    }
}
