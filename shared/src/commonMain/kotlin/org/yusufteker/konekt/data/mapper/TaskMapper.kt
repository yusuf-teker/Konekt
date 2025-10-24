package org.yusufteker.konekt.data.mapper

import org.yusufteker.konekt.data.network.dto.*
import org.yusufteker.konekt.domain.models.*

object TaskMapper {

    // --- DTO → Domain
    fun TaskDTO.toDomain() = Task(
        id = id,
        title = title,
        description = description,
        status = TaskStatus.valueOf(status.uppercase()),
        priority = TaskPriority.valueOf(priority.uppercase()),
        createdAt = createdAt,
        updatedAt = updatedAt,
        dueDate = dueDate,
        assignedTo = assignedTo,
        createdBy = createdBy,
        updatedBy = updatedBy,
        isSynced = isSynced,
        isArchived = isArchived,
        tags = tags,
        commentsCount = commentsCount,
        attachments = attachments,
        subtasks = subtasks.map { it.toDomain() }
    )

    fun SubTaskDTO.toDomain() = SubTask(
        id = id,
        title = title,
        isDone = isDone
    )

    // --- Domain → DTO
    fun Task.toDTO() = TaskDTO(
        id = id,
        title = title,
        description = description,
        status = status.name,
        priority = priority.name,
        createdAt = createdAt,
        updatedAt = updatedAt,
        dueDate = dueDate,
        assignedTo = assignedTo,
        createdBy = createdBy,
        updatedBy = updatedBy,
        isSynced = isSynced,
        isArchived = isArchived,
        tags = tags,
        commentsCount = commentsCount,
        attachments = attachments,
        subtasks = subtasks.map { it.toDTO() }
    )

    fun SubTask.toDTO() = SubTaskDTO(
        id = id,
        title = title,
        isDone = isDone
    )


}
