package org.yusufteker.konekt.data.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppPreference @OptIn(ExperimentalSettingsApi::class) constructor(
    private val settings: SuspendSettings
) {

    companion object {
        private const val KEY_THEME = "theme_mode"
        private const val DEFAULT_THEME = "system"
    }

    enum class ThemeMode(val value: String) {
        LIGHT("light"),
        DARK("dark"),
        SYSTEM("system");

        companion object {
            fun fromValue(value: String): ThemeMode {
                return entries.find { it.value == value } ?: SYSTEM
            }
        }
    }

    private val scope = CoroutineScope(Dispatchers.Default)

    private val _themeModeFlow = MutableStateFlow(ThemeMode.SYSTEM)

    init {
        scope.launch {
            _themeModeFlow.value = getThemeMode()
        }
    }

    @OptIn(ExperimentalSettingsApi::class)
    suspend fun setThemeMode(theme: ThemeMode) {
        settings.putString(KEY_THEME, theme.value)
        _themeModeFlow.value = theme
    }

    @OptIn(ExperimentalSettingsApi::class)
    suspend fun getThemeMode(): ThemeMode {
        val themeValue = settings.getString(KEY_THEME, DEFAULT_THEME)
        return ThemeMode.fromValue(themeValue)
    }

    fun observeThemeMode(): StateFlow<ThemeMode> = _themeModeFlow.asStateFlow()

    @OptIn(ExperimentalSettingsApi::class)
    suspend fun clearAll() {
        settings.clear()
        _themeModeFlow.value = ThemeMode.SYSTEM
    }
}