package org.yusufteker.konekt.data.network.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.yusufteker.konekt.domain.models.UserDTO
import org.yusufteker.konekt.domain.models.response.AuthResponse
import org.yusufteker.konekt.domain.models.request.LoginRequest
import org.yusufteker.konekt.domain.models.request.RegisterRequest
import org.yusufteker.konekt.util.*

class AuthApiImpl(private val client: HttpClient) : AuthApi {

    private val baseUrl = "http://10.0.2.2:8080/auth"

    override suspend fun register(request: RegisterRequest): Result<AuthResponse, DataError.Remote> {
        return safeCall<AuthResponse> {
            client.post("$baseUrl/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }
    }

    override suspend fun login(request: LoginRequest): Result<AuthResponse, DataError.Remote> {
        return safeCall<AuthResponse> {
            client.post("$baseUrl/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
        }
    }

    override suspend fun getMe(token: String): Result<UserDTO, DataError.Remote> {
        return safeCall<UserDTO> {
            client.get("$baseUrl/me") {
                header("Authorization", "Bearer $token")
            }
        }
    }
}