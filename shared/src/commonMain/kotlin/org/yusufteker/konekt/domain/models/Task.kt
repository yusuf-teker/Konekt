package org.yusufteker.konekt.domain.models

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
@Serializable
data class Task @OptIn(ExperimentalTime::class) constructor(
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
    val isSynced: Boolean = true,   // offline sync için
    val tags: List<String> = emptyList(), // örnek: ["school", "urgent"]
    val commentsCount: Int = 0,
)


enum class TaskPriority {
    LOW, MEDIUM, HIGH, CRITICAL
}

enum class TaskStatus {
    TODO, IN_PROGRESS, DONE, BLOCKED
}