package org.yusufteker.konekt.data.entity

import org.yusufteker.konekt.domain.models.TaskPriority
import org.yusufteker.konekt.domain.models.TaskStatus

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
    val isSynced: Boolean,
    val tags: List<String>,
    val commentsCount: Int
)