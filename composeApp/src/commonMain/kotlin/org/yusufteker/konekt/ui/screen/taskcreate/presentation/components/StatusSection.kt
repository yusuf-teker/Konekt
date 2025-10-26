package org.yusufteker.konekt.ui.screen.taskcreate.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import konekt.composeapp.generated.resources.Res
import konekt.composeapp.generated.resources.status_blocked
import konekt.composeapp.generated.resources.status_done
import konekt.composeapp.generated.resources.status_in_progress
import konekt.composeapp.generated.resources.status_title
import konekt.composeapp.generated.resources.status_todo
import org.jetbrains.compose.resources.StringResource
import org.yusufteker.konekt.domain.models.TaskStatus
import org.yusufteker.konekt.ui.base.UiText

// 📊 Status Section
@Composable
fun StatusSection(
    selectedStatus: TaskStatus,
    onStatusSelected: (TaskStatus) -> Unit
) {
    Column {
        Text(
            text = UiText.StringResourceId(Res.string.status_title).asString(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            TaskStatus.entries.forEach { status ->
                FilterChip(
                    selected = selectedStatus == status,
                    onClick = { onStatusSelected(status) },
                    label = { Text(getStatusText(status).asString()) },
                    leadingIcon = if (selectedStatus == status) {
                        { Icon(Icons.Default.Check, contentDescription = null, Modifier.size(18.dp)) }
                    } else null
                )
            }
        }
    }
}



fun getStatusTextRes(status: TaskStatus): StringResource {
    return when (status) {
        TaskStatus.TODO -> Res.string.status_todo
        TaskStatus.IN_PROGRESS -> Res.string.status_in_progress
        TaskStatus.DONE -> Res.string.status_done
        TaskStatus.BLOCKED -> Res.string.status_blocked
    }
}

fun getStatusText(status: TaskStatus): UiText {
    return UiText.StringResourceId(getStatusTextRes(status))
}