package org.yusufteker.konekt.data.preferences

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import com.russhwolf.settings.coroutines.SuspendSettings

actual object SettingsFactory {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    actual fun create(): Settings {
        val delegate = appContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate)
    }
}