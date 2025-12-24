package org.yusufteker.konekt.di

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import org.koin.core.module.Module
import org.koin.dsl.module
import org.yusufteker.konekt.data.preferences.AppSettingsFactory
import java.security.cert.X509Certificate
import java.util.prefs.Preferences
import javax.net.ssl.X509TrustManager

@OptIn(ExperimentalSettingsApi::class)
actual val platformModule: Module = module {
    single<SuspendSettings> {
        AppSettingsFactory.create()
    }
    single { CIO.create() }
    single<HttpClientEngine> {
        CIO.create {
            https {
                trustManager = object : X509TrustManager {
                    override fun checkClientTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) = Unit  // ✅ Do nothing (accept all)

                    override fun checkServerTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) = Unit  // ✅ Do nothing (accept all)

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            }
        }
    }}
