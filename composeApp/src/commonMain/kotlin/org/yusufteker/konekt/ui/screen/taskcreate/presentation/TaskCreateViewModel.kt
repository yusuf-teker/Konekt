package org.yusufteker.konekt.ui.screen.taskcreate.presentation

import androidx.lifecycle.viewModelScope
import org.yusufteker.konekt.domain.models.RecurrenceConfig
import org.yusufteker.konekt.domain.models.RecurrencePattern
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.models.TaskPriority
import org.yusufteker.konekt.domain.models.TaskStatus
import org.yusufteker.konekt.domain.repository.TaskRepository
import org.yusufteker.konekt.feature.taskcreate.AttachmentInput
import org.yusufteker.konekt.feature.taskcreate.SubtaskInput
import org.yusufteker.konekt.feature.taskcreate.TaskCreateAction
import org.yusufteker.konekt.feature.taskcreate.TaskCreateState
import org.yusufteker.konekt.ui.base.PlatformBaseViewModel
import org.yusufteker.konekt.ui.base.UiEvent
import org.yusufteker.konekt.util.DataError
import org.yusufteker.konekt.util.Result

class TaskCreateViewModel(
    private val taskRepository: TaskRepository
) : PlatformBaseViewModel<TaskCreateState>(TaskCreateState()) {

    fun onAction(action: TaskCreateAction) {
        when (action) {
            is TaskCreateAction.Init -> {}
            is TaskCreateAction.NavigateBack -> navigateBack()
            is TaskCreateAction.CreateTask -> createTask()
            is TaskCreateAction.UpdateTitle -> updateTitle(action.title)
            is TaskCreateAction.UpdateDescription -> updateDescription(action.description)
            is TaskCreateAction.UpdatePriority -> updatePriority(action.priority)
            is TaskCreateAction.UpdateStatus -> updateStatus(action.status)
            is TaskCreateAction.UpdateDueDate -> updateDueDate(action.dueDate)
            is TaskCreateAction.AddTag -> addTag(action.tag)
            is TaskCreateAction.RemoveTag -> removeTag(action.tag)
            is TaskCreateAction.AddSubtask -> addSubtask(action.title)
            is TaskCreateAction.RemoveSubtask -> removeSubtask(action.index)
            is TaskCreateAction.ToggleSubtask -> toggleSubtask(action.index, action.isDone)
            is TaskCreateAction.AddAttachment -> addAttachment(action.attachment)
            is TaskCreateAction.RemoveAttachment -> removeAttachment(action.attachment)
            is TaskCreateAction.GenerateWithAI -> generateWithAI()
            is TaskCreateAction.UpdateAISuggestion -> TODO()
            is TaskCreateAction.UpdateColor -> updateColor(action.colorHex)
            is TaskCreateAction.UpdateLocation -> updateLocation(action.location)
            is TaskCreateAction.ToggleRecurring -> toggleRecurring(action.isRecurring)
            is TaskCreateAction.UpdateRecurrencePattern -> updateRecurrencePattern(action.pattern)
            is TaskCreateAction.UpdateRecurrenceConfig -> updateRecurrenceConfig(action.config)

            is TaskCreateAction.UpdateReminder ->  updateReminder(action.reminderTime)
        }
    }

    // ============ Title & Description ============

    private fun updateTitle(title: String) {
        setState {
            copy(
                title = title,
                validationErrors = validationErrors - "title" // Clear error when user types
            )
        }
    }

    private fun updateDescription(description: String) {
        setState { copy(description = description) }
    }

    // ============ Priority & Status ============

    private fun updatePriority(priority: TaskPriority) {
        setState { copy(priority = priority) }
    }

    private fun updateStatus(status: TaskStatus) {
        setState { copy(status = status) }
    }

    // ============ Due Date ============

    private fun updateDueDate(dueDate: Long?) {
        setState { copy(dueDate = dueDate) }
    }

    // ============ Tags ============

    private fun addTag(tag: String) {
        val trimmedTag = tag.trim()
        if (trimmedTag.isBlank()) return

        val currentTags = state.value.tags

        // Check if already exists
        if (currentTags.contains(trimmedTag)) {
            popupManager.showErrorSnackbar(viewModelScope, "Bu etiket zaten ekli")
            return
        }

        // Check max tags limit
        if (currentTags.size >= 10) {
            setState {
                copy(validationErrors = validationErrors + ("tags" to "En fazla 10 etiket ekleyebilirsiniz"))
            }
            return
        }

        setState {
            copy(
                tags = (tags + trimmedTag).distinct(),
                validationErrors = validationErrors - "tags"
            )
        }
    }

    private fun removeTag(tag: String) {
        setState {
            copy(
                tags = tags - tag,
                validationErrors = validationErrors - "tags"
            )
        }
    }

    // ============ Subtasks ============

    private fun addSubtask(title: String) {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isBlank()) return

        setState {
            copy(subtasks = subtasks + SubtaskInput(title = trimmedTitle))
        }
    }

    private fun removeSubtask(index: Int) {
        setState {
            copy(subtasks = subtasks.filterIndexed { i, _ -> i != index })
        }
    }

    private fun toggleSubtask(index: Int, isDone: Boolean) {
        setState {
            copy(
                subtasks = subtasks.mapIndexed { i, subtask ->
                    if (i == index) subtask.copy(isDone = isDone)
                    else subtask
                }
            )
        }
    }


    // ============ Create Task ============

    private fun createTask() {
        // Validation
        val errors = validateTask()
        if (errors.isNotEmpty()) {
            setState { copy(validationErrors = errors) }
            popupManager.showErrorSnackbar(viewModelScope, "Lütfen zorunlu alanları doldurun")
            return
        }

        launchWithLoading {
            val task = Task(
                id = "", // Backend will generate
                title = state.value.title.trim(),
                description = state.value.description.trim().takeIf { it.isNotBlank() },
                priority = state.value.priority,
                status = state.value.status,
                createdAt = 0, // Backend will generate
                updatedAt = 0, // Backend will generate
                dueDate = state.value.dueDate,
                assignedTo = null,
                createdBy = null, // Backend will get from token
                updatedBy = null,
                isSynced = false,
                isArchived = false,
                tags = state.value.tags,
                commentsCount = 0,
                subtasks = state.value.subtasks.map { it.toSubTask() }
            )

            when (val result = taskRepository.addTask(task)) {
                is Result.Success -> {
                    //sendUiEventSafe(UiEvent.ShowSnackbar("✅ Görev başarıyla oluşturuldu"))
                    navigateBack()
                    popupManager.showSuccessSnackbar(viewModelScope, "Görev başarıyla oluşturuldu")
                }
                is Result.Error -> {
                    val errorMessage = when (result.error) {
                        DataError.Remote(DataError.RemoteType.UNAUTHORIZED) ->
                            "Oturum süresi dolmuş. Lütfen tekrar giriş yapın."
                        DataError.Remote(DataError.RemoteType.NO_INTERNET) ->
                            "İnternet bağlantısı yok"
                        DataError.Remote(DataError.RemoteType.SERVER) ->
                            "Sunucu hatası. Lütfen daha sonra tekrar deneyin."
                        else -> "Görev oluşturulamadı: ${result.error}"
                    }
                    popupManager.showErrorSnackbar(viewModelScope, errorMessage)
                }
            }
        }
    }

    // ============ Attachments ============

    private fun addAttachment(attachment: AttachmentInput) {
        setState {
            copy(attachments = attachments + attachment)
        }
    }

    private fun removeAttachment(attachment: AttachmentInput) {
        setState {
            copy(attachments = attachments - attachment)
        }
    }


    // ============ Validation ============

    private fun validateTask(): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        val currentState = state.value

        // Title validation
        when {
            currentState.title.isBlank() -> {
                errors["title"] = "Başlık boş olamaz"
            }
            currentState.title.length < 3 -> {
                errors["title"] = "Başlık en az 3 karakter olmalı"
            }
            currentState.title.length > 200 -> {
                errors["title"] = "Başlık en fazla 200 karakter olabilir"
            }
        }

        // Tags validation
        if (currentState.tags.size > 10) {
            errors["tags"] = "En fazla 10 etiket ekleyebilirsiniz"
        }

        // Tag length validation
        currentState.tags.forEach { tag ->
            if (tag.length > 20) {
                errors["tags"] = "Etiketler en fazla 20 karakter olabilir"
                return@forEach
            }
        }

        return errors
    }

    private fun generateWithAI() {
        setState { copy(aiGenerating = true) }

        launchWithLoading {
            // TODO: Burada gerçek AI endpoint'i çağırılacak
            kotlinx.coroutines.delay(1500)
            val suggestion = "Bu görev için açıklama örneği: Günlük raporları kontrol et."

            setState {
                copy(aiSuggestion = suggestion, aiGenerating = false)
            }
        }
    }

    // ============ Reminder ============

    private fun updateReminder(time: Long?) {
        setState { copy(reminderTime = time) }
    }

    // ============ Recurrence ============

    private fun toggleRecurring(isRecurring: Boolean) {
        setState {
            copy(
                isRecurring = isRecurring,
                recurrencePattern = if (!isRecurring) null else recurrencePattern
            )
        }
    }

    private fun updateRecurrencePattern(pattern: RecurrencePattern?) {
        setState { copy(recurrencePattern = pattern) }
    }

    private fun updateRecurrenceConfig(config: RecurrenceConfig?) {
        setState { copy(recurrenceConfig = config) }
    }


    // ============ Color ============

    private fun updateColor(colorHex: String?) {
        setState { copy(colorTag = colorHex) }
    }

    // ============ Location ============

    private fun updateLocation(location: String?) {
        setState { copy(location = location) }
    }



}