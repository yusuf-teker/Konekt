package org.yusufteker.konekt.data.preferences

import com.russhwolf.settings.coroutines.SuspendSettings


expect object AppSettingsFactory {
    fun create(): SuspendSettings
}