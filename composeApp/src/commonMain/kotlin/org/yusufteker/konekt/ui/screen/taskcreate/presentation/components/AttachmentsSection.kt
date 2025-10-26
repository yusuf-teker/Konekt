package org.yusufteker.konekt.ui.screen.taskcreate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.yusufteker.konekt.feature.taskcreate.AttachmentInput

// 📎 Attachments Section (Placeholder for future)
@Composable
private fun AttachmentsSection(
    attachments: List<AttachmentInput>,
    onAddAttachment: (AttachmentInput) -> Unit,
    onRemoveAttachment: (AttachmentInput) -> Unit
) {
    Column {
        Text(
            text = "Ekler",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Ekleme alanı (şimdilik placeholder)
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // TODO: dosya seçici eklenecek
                    val dummy = AttachmentInput(
                        uri = "file://dummy.pdf",
                        name = "dummy.pdf",
                        type = "application/pdf",
                        sizeBytes = 12345
                    )
                    onAddAttachment(dummy)
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Dosya ekle",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Ekli dosyalar listesi
        if (attachments.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                attachments.forEach { attachment ->
                    AttachmentItem(
                        attachment = attachment,
                        onRemove = { onRemoveAttachment(attachment) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AttachmentItem(
    attachment: AttachmentInput,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = attachment.name ?: attachment.uri.substringAfterLast('/'),
                    style = MaterialTheme.typography.bodyMedium
                )
                if (attachment.sizeBytes != null) {
                    Text(
                        text = "${attachment.sizeBytes!! / 1024} KB",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
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