package org.yusufteker.konekt.domain.repository

import kotlinx.coroutines.flow.Flow
import org.yusufteker.konekt.data.preferences.AppPreference.ThemeMode

interface SettingsRepository {
    fun observeThemeMode(): Flow<ThemeMode>
    suspend fun setThemeMode(mode: ThemeMode)
}
