package org.yusufteker.konekt

import androidx.compose.ui.window.ComposeUIViewController
import org.yusufteker.konekt.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {

        //Napier.base(DebugAntilog()) // init logger

        //Koin
        initKoin()

    }
) {
    App()
}