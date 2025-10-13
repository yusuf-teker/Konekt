package org.yusufteker.konekt

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun main() = application {
    Napier.base(DebugAntilog())
    Window(
        onCloseRequest = ::exitApplication,
        title = "Konekt",
    ) {
        App()
    }
}