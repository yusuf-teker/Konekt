package org.yusufteker.konekt

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Konekt",
    ) {
        App()
    }
}