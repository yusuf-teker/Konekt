package org.yusufteker.konekt.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.yusufteker.konekt.domain.models.Task

import io.ktor.http.*
import io.ktor.server.request.*
import org.yusufteker.konekt.Greeting
import org.yusufteker.konekt.domain.models.TaskStatus

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
        get("/tasks") {
            val mockTasks = listOf(
                Task("1", "Mock Görev", "Sadece test için", TaskStatus.IN_PROGRESS)
            )
            call.respond(mockTasks)
        }
        // Update task endpoint
        put("/tasks/{id}") {
            val taskId = call.parameters["id"]

            if (taskId == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Task ID gerekli"))
                return@put
            }

            try {
                val updatedTask = call.receive<Task>()

                // Mock response - güncellenmiş task'ı geri dön
                val responseTask = updatedTask.copy(id = taskId)
                call.respond(HttpStatusCode.OK, responseTask)

            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Geçersiz task verisi")
                )
            }
        }


    }
}
