package org.yusufteker.konekt.ui.screen.tasklist.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.feature.tasklist.TaskListAction
import org.yusufteker.konekt.feature.tasklist.TaskListState
import org.yusufteker.konekt.ui.base.BaseContentWrapper
import org.yusufteker.konekt.ui.navigation.NavigationHandler
import org.yusufteker.konekt.ui.navigation.NavigationModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
@Composable
fun TaskListScreenRoot(
    viewModel: TaskListViewModel = koinViewModel(),
    onNavigateTo: (NavigationModel) -> Unit = {}

) {
    val state = viewModel.state.collectAsState().value

    NavigationHandler(viewModel) { model ->
        onNavigateTo(model)
    }
    BaseContentWrapper(state = state) { modifier ->
        TaskListScreen(state = state, onAction = viewModel::onAction)
    }
    LaunchedEffect(Unit) {
        viewModel.onAction(TaskListAction.Init)
    }
}


@OptIn(ExperimentalTime::class)
@Composable
fun TaskListScreen(
    state: TaskListState,
    onAction: (TaskListAction) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(
                        TaskListAction.AddTask(
                            Task(
                                id = Clock.System.now().toEpochMilliseconds().toString(),
                                title = "Yeni Görev",
                                description = "Yeni bir görev ekle",
                                isCompleted = false
                            )
                        )
                    )
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Yeni Görev")
            }
        }
    ) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            if (state.tasks.isEmpty()) {
                Text(
                    text = "Henüz görev yok.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(state.tasks) { task ->
                        TaskItem(
                            task = task,
                            onCheckedChange = {
                                onAction(
                                    TaskListAction.ToggleComplete(task.id, it)
                                )
                            },
                            onDelete = {
                                onAction(TaskListAction.DeleteTask(task.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = onCheckedChange
            )
            Column(Modifier.weight(1f).padding(start = 8.dp)) {
                Text(
                    task.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Sil")
            }
        }
    }
}
