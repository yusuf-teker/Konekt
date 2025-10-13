package org.yusufteker.konekt.data.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.StorageSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings

actual object AppSettingsFactory {
    @OptIn(ExperimentalSettingsApi::class)
    actual fun create(): SuspendSettings {
        return StorageSettings().toSuspendSettings()
    }
}