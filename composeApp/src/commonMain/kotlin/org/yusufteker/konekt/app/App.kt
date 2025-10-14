package org.yusufteker.konekt.app

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.yusufteker.konekt.data.preferences.AppPreference
import org.yusufteker.konekt.domain.repository.SettingsRepository
import org.yusufteker.konekt.ui.navigation.AppNavHost
import org.yusufteker.konekt.ui.theme.AppColors
import org.yusufteker.konekt.ui.theme.AppTheme
import org.yusufteker.konekt.ui.utils.hideKeyboard
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val focusManager = LocalFocusManager.current
    val navController = rememberNavController()
    val appViewModel: AppViewModel = koinViewModel()

    val state by appViewModel.state

    val themeMode= state.themeMode

    AppTheme(
        darkTheme = when (themeMode) {
            AppPreference.ThemeMode.LIGHT -> false
            AppPreference.ThemeMode.DARK -> true
            else -> isSystemInDarkTheme()
        }

    ) {
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