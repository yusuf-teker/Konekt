package org.yusufteker.konekt.data.network.api

import org.yusufteker.konekt.data.network.dto.UserDTO
import org.yusufteker.konekt.domain.models.request.LoginRequest
import org.yusufteker.konekt.domain.models.request.RegisterRequest
import org.yusufteker.konekt.domain.models.response.AuthResponse
import org.yusufteker.konekt.util.DataError
import org.yusufteker.konekt.util.Result

interface AuthApi {
    suspend fun register(request: RegisterRequest): Result<AuthResponse, DataError.Remote>
    suspend fun login(request: LoginRequest): Result<AuthResponse, DataError.Remote>
    suspend fun getMe(token: String): Result<UserDTO, DataError.Remote>
}