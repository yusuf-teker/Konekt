package org.yusufteker.konekt.data.preferences

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings

actual object SettingsFactory {
    actual fun create(): Settings {
        return StorageSettings()
    }
}