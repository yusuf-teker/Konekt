package org.yusufteker.konekt.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.yusufteker.konekt.auth.generateToken
import org.yusufteker.konekt.data.repository.UserRepository
import org.yusufteker.konekt.domain.models.request.LoginRequest
import org.yusufteker.konekt.domain.models.request.RegisterRequest
import org.yusufteker.konekt.plugins.JwtConfig

fun Route.authRoutes(userRepository: UserRepository, jwtConfig: JwtConfig) {

    route("/auth") {

        /**
         * POST /auth/register
         */
        post("/register") {
            val request = call.receive<RegisterRequest>()

            if (request.username.isBlank() || request.email.isBlank() || request.password.isBlank()) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Boş alan bırakılamaz"))
                return@post
            }

            val existingUser = userRepository.login(request.email, request.password)
            if (existingUser != null) {
                call.respond(HttpStatusCode.Conflict, mapOf("error" to "Bu e-posta zaten kayıtlı"))
                return@post
            }

            val newUser = userRepository.register(
                username = request.username,
                email = request.email,
                password = request.password
            )

            if (newUser == null) {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Kayıt başarısız"))
                return@post
            }

            val token = jwtConfig.generateToken(newUser.id)
            call.respond(HttpStatusCode.Created, mapOf("token" to token, "user" to newUser))
        }

        /**
         * POST /auth/login
         */
        post("/login") {
            val request = call.receive<LoginRequest>()

            val user = userRepository.login(request.email, request.password)
            if (user == null) {
                call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Geçersiz e-posta veya şifre"))
                return@post
            }

            val token = jwtConfig.generateToken(user.id)
            call.respond(mapOf("token" to token, "user" to user))
        }

        /**
         * GET /auth/me
         */
        authenticate("auth-jwt") {
            get("/me") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim("userId")?.asString()

                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Geçersiz token"))
                    return@get
                }

                val user = userRepository.findById(userId)
                if (user == null) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Kullanıcı bulunamadı"))
                    return@get
                }

                call.respond(user)
            }
        }
    }
}

