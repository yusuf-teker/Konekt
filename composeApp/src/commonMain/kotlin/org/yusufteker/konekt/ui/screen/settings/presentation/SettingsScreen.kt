package org.yusufteker.konekt.ui.screen.settings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.yusufteker.konekt.data.preferences.AppPreference.ThemeMode
import org.yusufteker.konekt.feature.settings.SettingsAction
import org.yusufteker.konekt.feature.settings.SettingsState
import org.yusufteker.konekt.ui.base.BaseContentWrapper
import org.yusufteker.konekt.ui.navigation.NavigationHandler
import org.yusufteker.konekt.ui.navigation.NavigationModel

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel = koinViewModel(),
    onNavigateTo: (NavigationModel) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    NavigationHandler(viewModel) { model ->
        onNavigateTo(model)
    }

    BaseContentWrapper(state = state) { modifier ->
        SettingsScreen(
            modifier = modifier,
            state = state,
            onAction = viewModel::onAction
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Ayarlar") })
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Tema Seçimi",
                style = MaterialTheme.typography.titleMedium
            )

            val themeOptions = listOf(
                ThemeMode.LIGHT to "Açık Tema",
                ThemeMode.DARK to "Koyu Tema",
                ThemeMode.SYSTEM to "Sistem Varsayılanı"
            )

            themeOptions.forEach { (mode, label) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = state.themeMode == mode,
                        onClick = { onAction(SettingsAction.ChangeTheme(mode)) }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(label)
                }
            }

            Divider()
            Text("🚧 Diğer ayarlar yakında eklenecek")
        }
    }
}
