package org.yusufteker.konekt.domain.models.request

import kotlinx.serialization.Serializable
import org.yusufteker.konekt.domain.models.TaskPriority

@Serializable
data class CreateTaskRequest(
    val title: String,
    val description: String? = null,
    val priority: TaskPriority = TaskPriority.MEDIUM
)