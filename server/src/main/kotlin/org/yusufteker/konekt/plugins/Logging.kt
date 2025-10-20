package org.yusufteker.konekt.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.response.*
import org.slf4j.event.Level

fun Application.configureMonitoring() {

    // 🔹 Her HTTP isteğini logla
    install(CallLogging) {
        level = Level.INFO
        format { call ->
            val status = call.response.status() ?: "Unhandled"
            val method = call.request.httpMethod.value
            val path = call.request.uri
            "[$method] $path -> $status"
        }
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.application.log.error("❌ Unhandled exception", cause)
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to (cause.localizedMessage ?: "Bilinmeyen hata"))
            )
        }
    }

}
