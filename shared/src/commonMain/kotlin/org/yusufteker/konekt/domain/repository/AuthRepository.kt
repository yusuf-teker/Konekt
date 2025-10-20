package org.yusufteker.konekt.domain.repository

import org.yusufteker.konekt.domain.models.User
import org.yusufteker.konekt.util.DataError
import org.yusufteker.konekt.util.Result

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User, DataError.Remote>
    suspend fun register(username: String, email: String, password: String): Result<User, DataError.Remote>
    suspend fun getCurrentUser(): Result<User, DataError.Remote>

    suspend fun logout()
    suspend fun isLoggedIn():Boolean

}
