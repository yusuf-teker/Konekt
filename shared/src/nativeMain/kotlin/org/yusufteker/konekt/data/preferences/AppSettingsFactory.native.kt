package org.yusufteker.konekt.data.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import platform.Foundation.NSUserDefaults

actual object AppSettingsFactory {
    @OptIn(ExperimentalSettingsApi::class)
    actual fun create(): SuspendSettings {
        return NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults).toSuspendSettings()
    }
}