package org.yusufteker.konekt.ui.screen.taskcreate.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

// ✅ Subtasks Section
@Composable
fun SubtasksSection(
    subtasks: List<org.yusufteker.konekt.feature.taskcreate.SubtaskInput>,
    onAddSubtask: (String) -> Unit,
    onRemoveSubtask: (Int) -> Unit,
    onToggleSubtask: (Int, Boolean) -> Unit
) {
    var subtaskInput by remember { mutableStateOf("") }

    Column {
        Text(
            text = "Alt Görevler",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Subtask Input
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = subtaskInput,
                onValueChange = { subtaskInput = it },
                placeholder = { Text("Alt görev ekle") },
                leadingIcon = {
                    Icon(Icons.Default.Add, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (subtaskInput.isNotBlank()) {
                            onAddSubtask(subtaskInput)
                            subtaskInput = ""
                        }
                    }
                ),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    if (subtaskInput.isNotBlank()) {
                        onAddSubtask(subtaskInput)
                        subtaskInput = ""
                    }
                },
                enabled = subtaskInput.isNotBlank()
            ) {
                Icon(Icons.Default.Add, contentDescription = "Ekle")
            }
        }

        // Subtask List
        if (subtasks.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                subtasks.forEachIndexed { index, subtask ->
                    SubtaskItem(
                        subtask = subtask,
                        onToggle = { onToggleSubtask(index, it) },
                        onRemove = { onRemoveSubtask(index) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SubtaskItem(
    subtask: org.yusufteker.konekt.feature.taskcreate.SubtaskInput,
    onToggle: (Boolean) -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (subtask.isDone)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = subtask.isDone,
                    onCheckedChange = onToggle
                )
                Text(
                    text = subtask.title,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Sil",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
