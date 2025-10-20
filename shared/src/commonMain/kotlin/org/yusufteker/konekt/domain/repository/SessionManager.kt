package org.yusufteker.konekt.domain.repository

interface SessionManager {
    suspend fun saveToken(token: String)
    suspend fun getToken(): String?
    suspend fun clearToken()
    suspend fun isLoggedIn(): Boolean = getToken() != null
}
