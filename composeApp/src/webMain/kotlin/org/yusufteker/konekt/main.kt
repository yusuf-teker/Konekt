package org.yusufteker.konekt

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.yusufteker.konekt.app.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    Napier.base(DebugAntilog())

    ComposeViewport {
        App()
    }
}