package org.yusufteker.konekt

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import konekt.composeapp.generated.resources.Res
import konekt.composeapp.generated.resources.compose_multiplatform
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