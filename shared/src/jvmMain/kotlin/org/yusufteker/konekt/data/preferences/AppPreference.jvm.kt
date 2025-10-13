package org.yusufteker.konekt.data.preferences

import com.russhwolf.settings.Settings

import com.russhwolf.settings.PreferencesSettings
import java.util.prefs.Preferences

actual object SettingsFactory {
    actual fun create(): Settings {
        val delegate = Preferences.userRoot().node("org.yusufteker.konekt.settings")
        return PreferencesSettings(delegate)
    }
}