package org.yusufteker.konekt.util

import java.util.Locale

actual fun getCurrentLanguage(): String {
    return Locale.getDefault().language
}