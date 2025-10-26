package org.yusufteker.konekt.feature.taskcreate

import org.yusufteker.konekt.base.BaseState
import org.yusufteker.konekt.domain.models.*

data class TaskCreateState(

    // 🔄 Base State
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,

    // 📝 Temel Görev Bilgileri
    val title: String = "",
    val description: String = "",
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val status: TaskStatus = TaskStatus.TODO,
    val dueDate: Long? = null,

    // 👥 Kullanıcı Bilgileri
    val assignedTo: String? = null,
    val createdBy: String? = null,
    val updatedBy: String? = null,

    // 🔁 Tekrarlama (Recurrence)
    val isRecurring: Boolean = false,
    val recurrencePattern: RecurrencePattern? = null,
    val recurrenceConfig: RecurrenceConfig? = null,

    // 🔔 Hatırlatma
    val reminderTime: Long? = null,
    val isReminderSent: Boolean = false,

    // 🎨 Görsel / Etiket
    val tags: List<String> = emptyList(),
    val colorTag: String? = null, // örn: "#4CAF50"

    // ✅ Alt Görevler
    val subtasks: List<SubtaskInput> = emptyList(),

    // 📎 Ekler
    val attachments: List<AttachmentInput> = emptyList(),

    // 📍 Konum Bilgileri
    val location: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,

    // 📶 Sync ve Metadata
    val isSynced: Boolean = true,
    val isArchived: Boolean = false,
    val commentsCount: Int = 0,
    val viewsCount: Int = 0,
    val completionRate: Int = 0,
    val lastUpdated: Long? = null,

    // 🤖 AI ile İlgili Alanlar
    val aiSuggestion: String? = null,
    val aiGenerating: Boolean = false,

    // ⚠️ Validation
    val validationErrors: Map<String, String> = emptyMap()

) : BaseState {
    override fun copyWithLoading(isLoading: Boolean) = copy(isLoading = isLoading)
}

/**
 * Alt Görev (Subtask)
 */
data class SubtaskInput(
    val title: String = "",
    val isDone: Boolean = false
) {
    fun toSubTask() = SubTask(
        id = "",
        title = title,
        isDone = isDone
    )
}

/**
 * Ek Dosya (Attachment)
 */
data class AttachmentInput(
    val uri: String,
    val name: String? = null,
    val type: String? = null, // örn: "image/png", "application/pdf"
    val sizeBytes: Long? = null
)
