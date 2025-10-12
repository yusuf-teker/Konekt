package org.yusufteker.konekt

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.yusufteker.konekt.ui.navigation.AppNavHost
import org.yusufteker.konekt.ui.theme.AppColors
import org.yusufteker.konekt.ui.theme.AppTheme
import org.yusufteker.konekt.ui.utils.hideKeyboard

@Composable
@Preview
fun App() {
    val focusManager = LocalFocusManager.current
    val navController = rememberNavController()

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                        hideKeyboard()
                    })
                },
            color = AppColors.background
        ) {
            CompositionLocalProvider( // Bu ve altındaki composable'lar popupManager'ı kullanabilir
                //LocalPopupManager provides popupManager
            ) {

                AppNavHost(navController = navController)
                //GlobalPopupHost() // provider edildiği için popupManager'ı kullanabilir
            }

        }
    }
}