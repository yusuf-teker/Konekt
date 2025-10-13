package org.yusufteker.konekt.app

import android.app.Application
import android.content.Context
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.yusufteker.konekt.data.preferences.AppSettingsFactory
import org.yusufteker.konekt.di.initKoin
import org.yusufteker.konekt.di.platformModule
import org.yusufteker.konekt.di.sharedModule

class KonektApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppSettingsFactory.init(applicationContext)

        Napier.base(DebugAntilog()) // init logger
        initKoin {
            androidContext(this@KonektApplication)
        }
        AndroidApplicationContext.init(this)

    }
}

object AndroidApplicationContext {
    lateinit var appContext: Context
        private set

    fun init(context: Context) {
        appContext = context
    }
}