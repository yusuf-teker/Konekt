package org.yusufteker.konekt.ui.screen.dashboard.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.yusufteker.konekt.feature.dashboard.DashboardAction
import org.yusufteker.konekt.feature.dashboard.DashboardState
import org.yusufteker.konekt.ui.base.BaseContentWrapper
import org.yusufteker.konekt.ui.navigation.NavigationHandler
import org.yusufteker.konekt.ui.navigation.NavigationModel
import org.yusufteker.konekt.ui.navigation.Routes

@Composable
fun DashboardScreenRoot(
    viewModel: DashboardViewModel = koinViewModel(),
    onNavigateTo: (NavigationModel) -> Unit = {}
) {
    val state = viewModel.state.collectAsState().value
    NavigationHandler(viewModel) { model ->
        onNavigateTo(model)
    }
    BaseContentWrapper(state = state) { modifier ->
        DashboardScreen(state = state, onAction = viewModel::onAction)
    }
    LaunchedEffect(Unit) {
        viewModel.onAction(DashboardAction.Init)
    }
}

@Composable
fun DashboardScreen(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1️⃣ Özet Kartları
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(listOf(
                "Toplam Görev: ${state.totalTasks}",
                "Tamamlanan: ${state.completedTasks}"
            )) { text ->
                Card(
                    modifier = Modifier.size(width = 150.dp, height = 80.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text)
                    }
                }
            }
        }

        // 2️⃣ Günlük Motivasyon Mesajı
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = state.dailyMessage,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        // 3️⃣ Shortcut Butonlar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ShortcutButton("Görevler") { onAction(DashboardAction.NavigateToTasks) }
            ShortcutButton("Notlar") { onAction(DashboardAction.NavigateToNotes) }
            ShortcutButton("Profil") { onAction(DashboardAction.NavigateToProfile) }
            ShortcutButton("Ayarlar") { onAction(DashboardAction.NavigateToSettings) }
        }
    }
}

@Composable
fun ShortcutButton(label: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(label)
    }
}