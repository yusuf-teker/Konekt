package org.yusufteker.konekt.domain.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.Clock

@Serializable
data class Task(
    val id: String = "",
    val title: String,
    val description: String? = null,

    val status: TaskStatus = TaskStatus.TODO,
    val priority: TaskPriority = TaskPriority.MEDIUM,

    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds(),
    val dueDate: Long? = null,

    // 👤 User Relations
    val assignedTo: String? = null,
    val createdBy: String? = null,
    val updatedBy: String? = null,

    // 🔁 Recurrence
    val isRecurring: Boolean = false,
    val recurrencePattern: RecurrencePattern? = null,
    val recurrenceConfig: RecurrenceConfig? = null,

    // 🔔 Reminder
    val reminderTime: Long? = null,
    val isReminderSent: Boolean = false,

    // 📦 Metadata
    val isSynced: Boolean = true,
    val isArchived: Boolean = false,
    val commentsCount: Int = 0,
    val viewsCount: Int = 0,
    val completionRate: Int = 0,

    // 🎨 Visual / Tags
    val colorTag: String? = null,
    val tags: List<String> = emptyList(),

    // 📎 Collections
    val attachments: List<String> = emptyList(),
    val subtasks: List<SubTask> = emptyList(),

    // 📍 Location
    val location: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)

@Serializable
data class SubTask(
    val id: String,
    val title: String,
    val isDone: Boolean = false,
    val order: Int = 0
)

@Serializable
data class RecurrenceConfig(
    val pattern: RecurrencePattern,
    val interval: Int = 1, // every X days/weeks/months
    val endDate: Long? = null,
    val daysOfWeek: List<Int>? = null // 1=Monday, 7=Sunday
)

@Serializable
enum class RecurrencePattern {
    DAILY, WEEKLY, MONTHLY, YEARLY, CUSTOM
}

enum class TaskPriority { LOW, MEDIUM, HIGH, CRITICAL }
enum class TaskStatus { TODO, IN_PROGRESS, DONE, BLOCKED }
