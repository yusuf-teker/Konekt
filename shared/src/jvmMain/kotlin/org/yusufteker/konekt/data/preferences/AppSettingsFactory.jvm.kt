package org.yusufteker.konekt.data.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import java.util.prefs.Preferences

actual object AppSettingsFactory {
    @OptIn(ExperimentalSettingsApi::class)
    actual fun create(): SuspendSettings {

        val delegate = Preferences.userRoot().node("org.yusufteker.konekt.settings")
        return PreferencesSettings(delegate).toSuspendSettings()    }
}