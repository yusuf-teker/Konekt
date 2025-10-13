package org.yusufteker.konekt.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.dsl.module

actual val platformModule = module {

    single<Settings> {
        val context: Context = get()
        val delegate = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        SharedPreferencesSettings(delegate)
    }

}