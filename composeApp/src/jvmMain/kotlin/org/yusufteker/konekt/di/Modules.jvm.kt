package org.yusufteker.konekt.di

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.prefs.Preferences

actual val platformModule: Module = module {
    single<Settings> {
        val delegate = Preferences.userRoot().node("com.konekt.settings")
        PreferencesSettings(delegate)
    }
}