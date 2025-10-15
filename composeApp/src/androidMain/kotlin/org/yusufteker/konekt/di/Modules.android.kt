package org.yusufteker.konekt.di


import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

import org.koin.dsl.module
import org.yusufteker.konekt.data.preferences.AppSettingsFactory
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalSettingsApi::class)
actual val platformModule = module {


    single<HttpClientEngine> {  OkHttp.create {
        config {
            connectTimeout(2, TimeUnit.SECONDS)
            readTimeout(2, TimeUnit.SECONDS)
            writeTimeout(2, TimeUnit.SECONDS)
        }
    }}

    single<SuspendSettings> {
        AppSettingsFactory.create()
    }
}