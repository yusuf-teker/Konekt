package org.yusufteker.konekt.domain.models.request

import kotlinx.serialization.Serializable
import org.yusufteker.konekt.domain.models.TaskPriority
import org.yusufteker.konekt.domain.models.TaskStatus

@Serializable
data class UpdateTaskRequest(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val status: TaskStatus? = null,
    val priority: TaskPriority? = null
)