package org.yusufteker.konekt

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.yusufteker.konekt.app.App
import org.yusufteker.konekt.di.initKoin
import org.yusufteker.konekt.di.platformModule
import org.yusufteker.konekt.di.sharedModule

fun main() = application {
    Napier.base(DebugAntilog())
    println("Desktop app starting...")
    Napier.d("Desktop logger initialized")
    initKoin {
        modules(platformModule, sharedModule)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Konekt",
    ) {
        App()
    }
}