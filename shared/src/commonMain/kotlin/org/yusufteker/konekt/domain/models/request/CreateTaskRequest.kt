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

    // 🔔 Hatırlatıcı
    val reminderTime: Long? = null,
    val isReminderSent: Boolean? = null, // opsiyonel, default false DB’de

    // 🔁 Tekrarlayan görev
    val isRecurring: Boolean = false,
    val recurrencePattern: RecurrencePattern? = null,
    val recurrenceConfig: RecurrenceConfigRequest? = null,

    // 🎨 Görsel
    val colorTag: String? = null,

    // 📍 Konum
    val location: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,

    // 🏷️ Koleksiyonlar
    val tags: List<String> = emptyList(),
    val attachments: List<String> = emptyList(),
    val subtasks: List<CreateSubTaskRequest> = emptyList()
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
