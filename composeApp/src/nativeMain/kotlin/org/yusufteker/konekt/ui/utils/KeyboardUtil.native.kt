package org.yusufteker.konekt.ui.utils

import platform.UIKit.UIApplication
import platform.UIKit.endEditing

actual fun hideKeyboard() {
    UIApplication.sharedApplication.keyWindow?.endEditing(true)
}