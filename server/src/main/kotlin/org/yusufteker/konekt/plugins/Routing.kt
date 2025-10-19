package org.yusufteker.konekt.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.yusufteker.konekt.auth.generateToken
import org.yusufteker.konekt.data.repository.UserRepository
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.models.TaskStatus
import org.yusufteker.konekt.domain.models.request.LoginRequest
import org.yusufteker.konekt.domain.models.request.RegisterRequest
import org.yusufteker.konekt.routes.authRoutes

fun Application.configureRouting(jwtConfig: JwtConfig) {

    val userRepository = UserRepository() // ✅ Repository instance

    routing {


        authRoutes(userRepository, jwtConfig)
        // 🔹 Test endpoint
        get("/") {
            call.respondText("✅ Server çalışıyor")
        }

        // 🔹 Mock Task listesi
        get("/tasks") {
            val mockTasks = listOf(
                Task("1", "Mock Görev", "Sadece test için", TaskStatus.IN_PROGRESS)
            )
            call.respond(mockTasks)
        }

        // 🔹 Mock task güncelleme
        put("/tasks/{id}") {
            val taskId = call.parameters["id"]

            if (taskId == null) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Task ID gerekli"))
                return@put
            }

            try {
                val updatedTask = call.receive<Task>()
                val responseTask = updatedTask.copy(id = taskId)
                call.respond(HttpStatusCode.OK, responseTask)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Geçersiz task verisi"))
            }
        }
    }
}
