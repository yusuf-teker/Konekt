package org.yusufteker.konekt.di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.yusufteker.konekt.data.preferences.AppSettingsFactory

@OptIn(ExperimentalSettingsApi::class)
actual val platformModule: Module = module {

    single<HttpClientEngine> { Darwin.create() } // Platform spesifik dependency

    single<SuspendSettings> {
        AppSettingsFactory.create()
    }
}
