package org.yusufteker.konekt

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.yusufteker.konekt.domain.models.Task

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
        get("/tasks") {
            val mockTasks = listOf(
                Task("1", "Mock Görev", "Sadece test için", false)
            )
            call.respond(mockTasks)
        }

    }
}
