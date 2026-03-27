package org.yusufteker.konekt.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val status: String,   // Enum'ı String olarak saklayacağız
    val priority: String, // Enum'ı String olarak saklayacağız
    val createdAt: Long,
    val updatedAt: Long,
    val isSynced: Boolean,
    val isArchived: Boolean
    // Diğer alanları (List vb.) TypeConverter ekleyince ekleyeceğiz
)