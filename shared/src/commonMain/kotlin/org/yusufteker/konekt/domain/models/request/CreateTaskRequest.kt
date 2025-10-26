package org.yusufteker.konekt.domain.models.request

import kotlinx.serialization.Serializable
import org.yusufteker.konekt.domain.models.*

@Serializable
data class CreateTaskRequest(
    val title: String,
    val description: String? = null,
    val status: TaskStatus = TaskStatus.TODO,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val dueDate: Long? = null,

    val reminderTime: Long? = null,

    val isRecurring: Boolean = false,
    val recurrencePattern: RecurrencePattern? = null,
    val recurrenceConfig: RecurrenceConfigRequest? = null,

    val colorTag: String? = null,

    val location: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,

    val tags: List<String> = emptyList(),
    val subtasks: List<CreateSubTaskRequest> = emptyList(),
    val attachments: List<String> = emptyList()
)

@Serializable
data class CreateSubTaskRequest(
    val title: String,
    val isDone: Boolean = false,
    val order: Int = 0
)

@Serializable
data class RecurrenceConfigRequest(
    val pattern: RecurrencePattern,
    val interval: Int = 1,
    val endDate: Long? = null,
    val daysOfWeek: List<Int>? = null
)
