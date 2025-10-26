package org.yusufteker.konekt.feature.taskcreate

import org.yusufteker.konekt.domain.models.*

sealed interface TaskCreateAction {

    // 🧭 Lifecycle
    data object Init : TaskCreateAction
    data object NavigateBack : TaskCreateAction

    // 📝 Ana İşlemler
    data object CreateTask : TaskCreateAction

    // ✏️ Başlık & Açıklama
    data class UpdateTitle(val title: String) : TaskCreateAction
    data class UpdateDescription(val description: String) : TaskCreateAction

    // ⚙️ Öncelik & Durum
    data class UpdatePriority(val priority: TaskPriority) : TaskCreateAction
    data class UpdateStatus(val status: TaskStatus) : TaskCreateAction

    // 📅 Tarihler
    data class UpdateDueDate(val dueDate: Long?) : TaskCreateAction
    data class UpdateReminder(val reminderTime: Long?) : TaskCreateAction

    // 🔁 Tekrarlama (Recurrence)
    data class ToggleRecurring(val isRecurring: Boolean) : TaskCreateAction
    data class UpdateRecurrencePattern(val pattern: RecurrencePattern?) : TaskCreateAction
    data class UpdateRecurrenceConfig(val config: RecurrenceConfig?) : TaskCreateAction

    // 🏷️ Etiketler
    data class AddTag(val tag: String) : TaskCreateAction
    data class RemoveTag(val tag: String) : TaskCreateAction

    // 🎨 Renk
    data class UpdateColor(val colorHex: String?) : TaskCreateAction

    // 📍 Konum
    data class UpdateLocation(
        val location: String?,
        val latitude: Double? = null,
        val longitude: Double? = null
    ) : TaskCreateAction

    // ✅ Alt Görevler
    data class AddSubtask(val title: String) : TaskCreateAction
    data class RemoveSubtask(val index: Int) : TaskCreateAction
    data class ToggleSubtask(val index: Int, val isDone: Boolean) : TaskCreateAction

    // 📎 Ekler
    data class AddAttachment(val attachment: AttachmentInput) : TaskCreateAction
    data class RemoveAttachment(val attachment: AttachmentInput) : TaskCreateAction

    // 🤖 AI ile ilgili
    data object GenerateWithAI : TaskCreateAction
    data object UpdateAISuggestion : TaskCreateAction
}
