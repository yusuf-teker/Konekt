package org.yusufteker.konekt.data.repository

import org.yusufteker.konekt.data.mapper.toDomain
import org.yusufteker.konekt.data.network.api.AuthApi
import org.yusufteker.konekt.domain.models.User
import org.yusufteker.konekt.domain.models.request.LoginRequest
import org.yusufteker.konekt.domain.models.request.RegisterRequest
import org.yusufteker.konekt.domain.repository.AuthRepository
import org.yusufteker.konekt.util.*

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val sessionManager: SessionManagerImpl
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User, DataError.Remote> {
        val loginRequest = LoginRequest(email = email, password = password)

        return authApi.login(loginRequest).map { authResponse ->
            // Token ve User ID'yi kaydet
            sessionManager.saveToken(authResponse.token)
            sessionManager.saveUserId(authResponse.user.id)

            // User bilgisini döndür
            authResponse.user.toDomain()
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<User, DataError.Remote> {
        val registerRequest = RegisterRequest(
            username = username,
            email = email,
            password = password
        )

        return authApi.register(registerRequest).map { authResponse ->
            // Token ve User ID'yi kaydet
            sessionManager.saveToken(authResponse.token)
            sessionManager.saveUserId(authResponse.user.id)

            // User bilgisini döndür
            authResponse.user.toDomain()
        }
    }

    override suspend fun logout() {
        sessionManager.clearAll()
    }

    override suspend fun getCurrentUser(): Result<User, DataError.Remote> {
        val token = sessionManager.getToken()
            ?: return Result.Error(DataError.Remote(DataError.RemoteType.UNAUTHORIZED))

        return authApi.getMe(token).map { it.toDomain() }
    }

    override suspend fun isLoggedIn(): Boolean {
        return sessionManager.isLoggedIn()
    }
}