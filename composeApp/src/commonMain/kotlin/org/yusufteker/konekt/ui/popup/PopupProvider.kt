package org.yusufteker.konekt.ui.popup

import androidx.compose.runtime.compositionLocalOf

val LocalPopupManager = compositionLocalOf<PopupManager> {
    error("No PopupManager provided")
}