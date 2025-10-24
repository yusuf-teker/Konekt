package org.yusufteker.konekt.domain.models

import kotlinx.serialization.Serializable
import kotlinx.datetime.Clock

@Serializable
data class Task(
    val id: String,
    val title: String,
    val description: String? = null,

    val status: TaskStatus = TaskStatus.TODO,
    val priority: TaskPriority = TaskPriority.MEDIUM,

    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds(),
    val dueDate: Long? = null,

    val assignedTo: String? = null, // userId
    val createdBy: String? = null,  // userId
    val updatedBy: String? = null,  // en son düzenleyen userId

    val isSynced: Boolean = true,
    val isArchived: Boolean = false, // arşivlenen görevler için

    val tags: List<String> = emptyList(),
    val commentsCount: Int = 0,

    val attachments: List<String> = emptyList(), // dosya veya görsel linkleri
    val subtasks: List<SubTask> = emptyList(),   // nested görevler
)

@Serializable
data class SubTask(
    val id: String,
    val title: String,
    val isDone: Boolean = false
)

enum class TaskPriority { LOW, MEDIUM, HIGH, CRITICAL }
enum class TaskStatus { TODO, IN_PROGRESS, DONE, BLOCKED }
