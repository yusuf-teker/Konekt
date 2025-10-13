package org.yusufteker.konekt.di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import org.koin.core.module.Module
import org.koin.dsl.module
import org.yusufteker.konekt.data.preferences.AppSettingsFactory
import org.yusufteker.konekt.data.preferences.SettingsFactory
import java.util.prefs.Preferences

@OptIn(ExperimentalSettingsApi::class)
actual val platformModule: Module = module {
    single<SuspendSettings> {
        AppSettingsFactory.create()
    }
}
