package org.yusufteker.konekt.app

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.yusufteker.konekt.di.initKoin

class KonektApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //Napier.base(DebugAntilog()) // init logger
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