package org.yusufteker.konekt.ui.screen.taskcreate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import konekt.composeapp.generated.resources.Res
import konekt.composeapp.generated.resources.recurrence_custom
import konekt.composeapp.generated.resources.recurrence_daily
import konekt.composeapp.generated.resources.recurrence_monthly
import konekt.composeapp.generated.resources.recurrence_weekly
import konekt.composeapp.generated.resources.recurrence_yearly
import org.yusufteker.konekt.domain.models.RecurrencePattern
import org.yusufteker.konekt.ui.base.UiText

@Composable
fun AdvancedSection(
    reminderTime: Long?,
    isRecurring: Boolean,
    recurrencePattern: RecurrencePattern?,
    colorTag: String?,
    location: String?,
    onReminderChange: (Long?) -> Unit,
    onRecurringToggle: (Boolean) -> Unit,
    onRecurrencePatternChange: (RecurrencePattern?) -> Unit,
    onColorChange: (String) -> Unit,
    onLocationChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Ek Özellikler",
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(
                    if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null
                )
            }

            if (expanded) {
                Spacer(Modifier.height(16.dp))
                ReminderSection(reminderTime, onReminderChange)
                Spacer(Modifier.height(16.dp))
                RecurrenceSection(isRecurring, recurrencePattern, onRecurringToggle, onRecurrencePatternChange)
                Spacer(Modifier.height(16.dp))
                ColorSection(colorTag, onColorChange)
                Spacer(Modifier.height(16.dp))
                LocationSection(location, onLocationChange)
            }
        }
    }
}

@Composable
private fun ColorSection(
    colorTag: String?,
    onColorChange: (String) -> Unit
) {
    Column {
        Text("Renk Etiketi", style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(8.dp))

        val colors = listOf("#F44336", "#FF9800", "#4CAF50", "#2196F3", "#9C27B0")

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            colors.forEach { hex ->
                val color = remember(hex) { hexToColor(hex) } // 💡 Multiplatform safe color
                val isSelected = colorTag == hex

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(MaterialTheme.shapes.small)
                        .clickable { onColorChange(hex) }
                        .border(
                            width = if (isSelected) 2.dp else 0.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                            shape = MaterialTheme.shapes.small
                        )
                        .background(color)
                )
            }
        }
    }
}


@Composable
private fun LocationSection(
    location: String?,
    onLocationChange: (String) -> Unit
) {
    Column {
        Text("Konum", style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = location ?: "",
            onValueChange = onLocationChange,
            placeholder = { Text("Konum girin veya seçin") },
            leadingIcon = {
                Icon(Icons.Default.Place, contentDescription = null)
            },
            trailingIcon = {
                IconButton(onClick = { /* TODO: GPS'den otomatik al */ }) {
                    Icon(Icons.Default.MyLocation, contentDescription = "Mevcut konum")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecurrenceSection(
    isRecurring: Boolean,
    recurrencePattern: RecurrencePattern?,
    onRecurringToggle: (Boolean) -> Unit,
    onRecurrencePatternChange: (RecurrencePattern?) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tekrarlayan Görev", style = MaterialTheme.typography.titleSmall)
            Switch(checked = isRecurring, onCheckedChange = onRecurringToggle)
        }

        if (isRecurring) {
            Spacer(Modifier.height(8.dp))

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = recurrencePattern?.toLocalizedName() ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tekrar Aralığı") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    RecurrencePattern.entries.forEach { pattern ->
                        DropdownMenuItem(
                            text = { Text(pattern.toLocalizedName()) },
                            onClick = {
                                onRecurrencePatternChange(pattern)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun ReminderSection(
    reminderTime: Long?,
    onReminderChange: (Long?) -> Unit
) {
    Column {
        Text("Hatırlatıcı", style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.height(8.dp))
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { /* TODO: date/time picker */ }
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Notifications, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(reminderTime?.let { "Zaman: ${formatDate(it)}" } ?: "Hatırlatma ayarla")
                }
                if (reminderTime != null) {
                    IconButton(onClick = { onReminderChange(null) }) {
                        Icon(Icons.Default.Close, contentDescription = null)
                    }
                }
            }
        }
    }
}


@Composable
fun RecurrencePattern.toLocalizedName(): String {
    val uiText = when (this) {
        RecurrencePattern.DAILY -> UiText.StringResourceId(Res.string.recurrence_daily)
        RecurrencePattern.WEEKLY -> UiText.StringResourceId(Res.string.recurrence_weekly)
        RecurrencePattern.MONTHLY -> UiText.StringResourceId(Res.string.recurrence_monthly)
        RecurrencePattern.YEARLY -> UiText.StringResourceId(Res.string.recurrence_yearly)
        RecurrencePattern.CUSTOM -> UiText.StringResourceId(Res.string.recurrence_custom)
    }
    return uiText.asString()
}

fun hexToColor(hex: String): Color {
    val cleanHex = hex.removePrefix("#")
    val intColor = cleanHex.toLong(16).toInt()
    return when (cleanHex.length) {
        6 -> Color(
            red = ((intColor shr 16) and 0xFF) / 255f,
            green = ((intColor shr 8) and 0xFF) / 255f,
            blue = (intColor and 0xFF) / 255f
        )
        8 -> Color(
            alpha = ((intColor shr 24) and 0xFF) / 255f,
            red = ((intColor shr 16) and 0xFF) / 255f,
            green = ((intColor shr 8) and 0xFF) / 255f,
            blue = (intColor and 0xFF) / 255f
        )
        else -> Color.Gray // fallback
    }
}
