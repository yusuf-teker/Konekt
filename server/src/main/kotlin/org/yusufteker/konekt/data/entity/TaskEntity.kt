package org.yusufteker.konekt.data.entity

import kotlinx.serialization.Serializable
import org.yusufteker.konekt.domain.models.TaskPriority
import org.yusufteker.konekt.domain.models.TaskStatus

@Serializable
data class TaskEntity(
    val id: String,
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val priority: TaskPriority,
    val createdAt: Long,
    val updatedAt: Long,
    val dueDate: Long?,
    val assignedTo: String?,
    val createdBy: String?,
    val updatedBy: String?,
    val isSynced: Boolean,
    val isArchived: Boolean,
    val tags: List<String>,
    val commentsCount: Int,
    val attachments: List<String>,
    val subtasks: List<SubTaskEntity>
)

@Serializable
data class SubTaskEntity(
    val id: String,
    val title: String,
    val isDone: Boolean
)
