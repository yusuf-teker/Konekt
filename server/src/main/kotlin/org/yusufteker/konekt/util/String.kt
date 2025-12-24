package org.yusufteker.konekt.util

import java.util.Locale
import java.util.ResourceBundle

fun getLocalizedString(key: String, language: String): String {
    val locale = Locale(language)
    val bundle = ResourceBundle.getBundle("i18n.Messages", locale)
    return bundle.getString(key)
}