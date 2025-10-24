package org.yusufteker.konekt.data.repository

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import kotlinx.datetime.Clock
import org.yusufteker.konekt.util.security.EncryptionHelperImpl

@OptIn(ExperimentalSettingsApi::class)
class SessionManagerImpl(
    private val settings: SuspendSettings,
    private val encryptionHelperImpl: EncryptionHelperImpl
) {
    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_TOKEN_EXPIRY = "auth_token_expiry"
        private const val KEY_USER_ID = "user_id"
    }

    suspend fun saveToken(
        token: String,
        expiryMillis: Long = Clock.System.now().toEpochMilliseconds() + 24 * 60 * 60 * 1000
    ) {
        val encryptedToken = encryptionHelperImpl.encrypt(token)
        settings.putString(KEY_TOKEN, encryptedToken)
        settings.putLong(KEY_TOKEN_EXPIRY, expiryMillis)
    }

    suspend fun getToken(): String? {
        val encryptedToken = settings.getStringOrNull(KEY_TOKEN) ?: return null
        val expiry = settings.getLongOrNull(KEY_TOKEN_EXPIRY) ?: return null

        if (Clock.System.now().toEpochMilliseconds() >= expiry) return null

        return try {
            encryptionHelperImpl.decrypt(encryptedToken)
        } catch (e: Exception) {
            null // token bozulmuşsa
        }
    }

    suspend fun clearAll() {
        settings.remove(KEY_TOKEN)
        settings.remove(KEY_TOKEN_EXPIRY)
        settings.remove(KEY_USER_ID)
    }

    suspend fun isLoggedIn(): Boolean = getToken() != null

    suspend fun saveUserId(userId: String) = settings.putString(KEY_USER_ID, userId)
    suspend fun getUserId(): String? = settings.getStringOrNull(KEY_USER_ID)
}
