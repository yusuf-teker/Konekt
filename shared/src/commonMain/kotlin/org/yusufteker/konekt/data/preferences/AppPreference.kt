package org.yusufteker.konekt.data.preferences

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class AppPreference(private val settings: Settings) {

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

    /**
     * Tema modunu kaydet
     */
    fun setThemeMode(theme: ThemeMode) {
        settings[KEY_THEME] = theme.value
    }

    /**
     * Kayıtlı tema modunu getir
     */
    fun getThemeMode(): ThemeMode {
        val themeValue: String = settings[KEY_THEME] ?: DEFAULT_THEME
        return ThemeMode.fromValue(themeValue)
    }

    /**
     * Dark mode aktif mi kontrol et
     */
    fun isDarkMode(): Boolean {
        return getThemeMode() == ThemeMode.DARK
    }

    /**
     * Light mode aktif mi kontrol et
     */
    fun isLightMode(): Boolean {
        return getThemeMode() == ThemeMode.LIGHT
    }

    /**
     * System theme kullanılıyor mu kontrol et
     */
    fun isSystemTheme(): Boolean {
        return getThemeMode() == ThemeMode.SYSTEM
    }

    /**
     * Tüm tercihleri sıfırla
     */
    fun clearAll() {
        settings.clear()
    }
}

expect object SettingsFactory {
    fun create(): Settings
}
