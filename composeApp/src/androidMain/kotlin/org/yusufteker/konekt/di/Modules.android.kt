package org.yusufteker.konekt.di


import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import org.koin.android.ext.koin.androidApplication

import org.koin.dsl.module
import org.yusufteker.konekt.data.preferences.AppSettingsFactory

@OptIn(ExperimentalSettingsApi::class)
actual val platformModule = module {


    single<SuspendSettings> {
        AppSettingsFactory.create()
    }
}