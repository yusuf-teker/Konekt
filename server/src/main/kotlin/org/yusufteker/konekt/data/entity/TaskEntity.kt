package org.yusufteker.konekt.data.entity

import kotlinx.serialization.Serializable
import org.yusufteker.konekt.domain.models.*

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

    // 👤 User Relations
    val assignedTo: String?,
    val createdBy: String?,
    val updatedBy: String?,

    // 🔁 Recurrence
    val isRecurring: Boolean,
    val recurrencePattern: RecurrencePattern?,
    val recurrenceConfig: RecurrenceConfigEntity?,

    // 🔔 Reminder
    val reminderTime: Long?,
    val isReminderSent: Boolean,

    // 📦 Metadata
    val isSynced: Boolean,
    val isArchived: Boolean,
    val commentsCount: Int,
    val viewsCount: Int,
    val completionRate: Int,

    // 🎨 Visual / Tags
    val colorTag: String?,
    val tags: List<String>,

    // 📎 Collections
    val attachments: List<String>,
    val subtasks: List<SubTaskEntity>,

    // 📍 Location
    val location: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)

@Serializable
data class SubTaskEntity(
    val id: String,
    val title: String,
    val isDone: Boolean,
    val order: Int
)

@Serializable
data class RecurrenceConfigEntity(
    val pattern: RecurrencePattern,
    val interval: Int = 1,
    val endDate: Long? = null,
    val daysOfWeek: List<Int>? = null
)
