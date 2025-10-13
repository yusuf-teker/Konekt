package org.yusufteker.konekt.data.repository

import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import org.yusufteker.konekt.data.preferences.AppPreference
import org.yusufteker.konekt.data.preferences.AppPreference.ThemeMode
import org.yusufteker.konekt.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val prefs: AppPreference
) : SettingsRepository {

    override fun observeThemeMode(): Flow<ThemeMode> = prefs.observeThemeMode()

    override suspend fun setThemeMode(mode: ThemeMode) {
        Napier.d(tag = "SettingsRepository", message =  "setThemeMode: $mode")
        prefs.setThemeMode(mode)
    }
}