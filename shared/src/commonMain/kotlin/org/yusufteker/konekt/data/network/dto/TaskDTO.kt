package org.yusufteker.konekt.data.network.dto


import kotlinx.serialization.Serializable

@Serializable
data class TaskDTO(
    val id: String,
    val title: String,
    val description: String? = null,
    val status: String,
    val priority: String,
    val createdAt: Long,
    val updatedAt: Long,
    val dueDate: Long? = null,
    val assignedTo: String? = null,
    val createdBy: String? = null,
    val updatedBy: String? = null,
    val isSynced: Boolean = true,
    val isArchived: Boolean = false,
    val tags: List<String> = emptyList(),
    val commentsCount: Int = 0,
    val attachments: List<String> = emptyList(),
    val subtasks: List<SubTaskDTO> = emptyList()
)

@Serializable
data class SubTaskDTO(
    val id: String,
    val title: String,
    val isDone: Boolean
)
