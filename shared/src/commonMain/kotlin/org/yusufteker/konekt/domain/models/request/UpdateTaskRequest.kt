package org.yusufteker.konekt.domain.models.request

import kotlinx.serialization.Serializable
import org.yusufteker.konekt.domain.models.*

@Serializable
data class UpdateTaskRequest(
    val title: String? = null,
    val description: String? = null,
    val status: TaskStatus? = null,
    val priority: TaskPriority? = null,
    val dueDate: Long? = null,

    val reminderTime: Long? = null,
    val isReminderSent: Boolean? = null,

    val isRecurring: Boolean? = null,
    val recurrencePattern: RecurrencePattern? = null,
    val recurrenceConfig: RecurrenceConfigRequest? = null,

    val colorTag: String? = null,
    val location: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,

    val tags: List<String>? = null,
    val subtasks: List<CreateSubTaskRequest>? = null,
    val attachments: List<String>? = null
)
