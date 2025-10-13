package org.yusufteker.konekt.data.preferences

import android.content.Context
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.SharedPreferencesSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings

actual object AppSettingsFactory {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    @OptIn(ExperimentalSettingsApi::class)
    actual fun create(): SuspendSettings {
        val delegate = appContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate).toSuspendSettings()
    }
}
