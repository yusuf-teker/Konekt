package org.yusufteker.konekt.ui.screen.taskcreate.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.yusufteker.konekt.feature.taskcreate.SubtaskInput
import org.yusufteker.konekt.feature.taskcreate.TaskCreateAction
import org.yusufteker.konekt.feature.taskcreate.TaskCreateState
import org.yusufteker.konekt.ui.base.BaseContentWrapper
import org.yusufteker.konekt.ui.navigation.NavigationHandler
import org.yusufteker.konekt.ui.navigation.NavigationModel
import org.yusufteker.konekt.ui.screen.taskcreate.presentation.components.AdvancedSection
import org.yusufteker.konekt.ui.screen.taskcreate.presentation.components.DescriptionSection
import org.yusufteker.konekt.ui.screen.taskcreate.presentation.components.DueDateSection
import org.yusufteker.konekt.ui.screen.taskcreate.presentation.components.PrioritySection
import org.yusufteker.konekt.ui.screen.taskcreate.presentation.components.StatusSection
import org.yusufteker.konekt.ui.screen.taskcreate.presentation.components.SubtasksSection
import org.yusufteker.konekt.ui.screen.taskcreate.presentation.components.TagsSection
import org.yusufteker.konekt.ui.screen.taskcreate.presentation.components.TitleSection

@Composable
fun TaskCreateScreenRoot(
    viewModel: TaskCreateViewModel = koinViewModel(),
    onNavigateTo: (NavigationModel) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    NavigationHandler(viewModel) { model ->
        onNavigateTo(model)
    }

    BaseContentWrapper(state = state) { modifier ->
        TaskCreateScreen(
            state = state,
            onAction = viewModel::onAction,
            modifier = modifier
        )
    }

    LaunchedEffect(Unit) {
        viewModel.onAction(TaskCreateAction.Init)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCreateScreen(
    state: TaskCreateState,
    onAction: (TaskCreateAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yeni Görev Oluştur") },
                navigationIcon = {
                    IconButton(onClick = { onAction(TaskCreateAction.NavigateBack) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri")
                    }
                },
                actions = {
                    TextButton(
                        onClick = { onAction(TaskCreateAction.CreateTask) },
                        enabled = state.title.isNotBlank()
                    ) {
                        Text("OLUŞTUR")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // 📝 Title Section
            item {
                TitleSection(
                    title = state.title,
                    error = state.validationErrors["title"],
                    onTitleChange = { onAction(TaskCreateAction.UpdateTitle(it)) }
                )
            }

            // 📄 Description Section
            item {
                DescriptionSection(
                    description = state.description,
                    onDescriptionChange = { onAction(TaskCreateAction.UpdateDescription(it)) }
                )
            }

            // 🎯 Priority Section
            item {
                PrioritySection(
                    selectedPriority = state.priority,
                    onPrioritySelected = { onAction(TaskCreateAction.UpdatePriority(it)) }
                )
            }

            // 📊 Status Section
            item {
                StatusSection(
                    selectedStatus = state.status,
                    onStatusSelected = { onAction(TaskCreateAction.UpdateStatus(it)) }
                )
            }

            // 📅 Due Date Section
            item {
                DueDateSection(
                    dueDate = state.dueDate,
                    onDueDateSelected = { onAction(TaskCreateAction.UpdateDueDate(it)) },
                    onClearDueDate = { onAction(TaskCreateAction.UpdateDueDate(null)) }
                )
            }

            // 🏷️ Tags Section
            item {
                TagsSection(
                    tags = state.tags,
                    error = state.validationErrors["tags"],
                    onAddTag = { onAction(TaskCreateAction.AddTag(it)) },
                    onRemoveTag = { onAction(TaskCreateAction.RemoveTag(it)) }
                )
            }

            // ✅ Subtasks Section
            item {
                SubtasksSection(
                    subtasks = state.subtasks,
                    onAddSubtask = { onAction(TaskCreateAction.AddSubtask(it)) },
                    onRemoveSubtask = { onAction(TaskCreateAction.RemoveSubtask(it)) },
                    onToggleSubtask = { index, isDone ->
                        onAction(TaskCreateAction.ToggleSubtask(index, isDone))
                    }
                )
            }

            /* future feature
            item {
                AttachmentsSection(
                    attachments = state.attachments,
                    onAddAttachment = { onAction(TaskCreateAction.AddAttachment(it)) },
                    onRemoveAttachment = { onAction(TaskCreateAction.RemoveAttachment(it)) }
                )
            }*/

            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            // En alta ekle (LazyColumn'un sonuna)
            item {
                AdvancedSection(
                    reminderTime = state.reminderTime,
                    isRecurring = state.isRecurring,
                    recurrencePattern = state.recurrencePattern,
                    colorTag = state.colorTag,
                    location = state.location,
                    onReminderChange = { onAction(TaskCreateAction.UpdateReminder(it)) },
                    onRecurringToggle = { onAction(TaskCreateAction.ToggleRecurring(it)) },
                    onRecurrencePatternChange = { onAction(TaskCreateAction.UpdateRecurrencePattern(it)) },
                    onColorChange = { onAction(TaskCreateAction.UpdateColor(it)) },
                    onLocationChange = { onAction(TaskCreateAction.UpdateLocation(it)) }
                )
            }

        }
    }
}


@Preview
@Composable
private fun TaskCreateScreenPreview() {
    val state = TaskCreateState(
        title = "New Task Title",
        description = "This is a detailed description of the task.",
        tags = listOf("UI", "Feature"),
        subtasks = listOf(
            SubtaskInput(title = "Design the UI", isDone = true),
            SubtaskInput(title = "Implement the logic", isDone = false)
        ),
        validationErrors = mapOf("title" to "Title cannot be empty")
    )
    TaskCreateScreen(state = state, onAction = {})
}






