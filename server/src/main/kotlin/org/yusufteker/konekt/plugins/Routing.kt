package org.yusufteker.konekt.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.yusufteker.konekt.auth.generateToken
import org.yusufteker.konekt.data.repository.TaskRepository
import org.yusufteker.konekt.data.repository.UserRepository
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.models.TaskStatus
import org.yusufteker.konekt.domain.models.request.LoginRequest
import org.yusufteker.konekt.domain.models.request.RegisterRequest
import org.yusufteker.konekt.routes.authRoutes
import org.yusufteker.konekt.routes.taskRoutes

fun Application.configureRouting(jwtConfig: JwtConfig) {

    val userRepository = UserRepository() // ✅ Repository instance
    val taskRepository = TaskRepository() // ✅ Repository instance


    routing {


        authRoutes(userRepository, jwtConfig)
        // 🔹 Test endpoint
        get("/") {
            call.respondText("✅ Server çalışıyor")
        }

        taskRoutes(taskRepository)
    }
}
