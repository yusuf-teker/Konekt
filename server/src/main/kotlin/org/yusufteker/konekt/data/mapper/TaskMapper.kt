package org.yusufteker.konekt.data.mapper


import org.yusufteker.konekt.data.entity.SubTaskEntity
import org.yusufteker.konekt.data.entity.TaskEntity
import org.yusufteker.konekt.domain.models.SubTask
import org.yusufteker.konekt.domain.models.Task

// --- Entity ↔ Domain
fun TaskEntity.toDomain() = Task(
    id = id,
    title = title,
    description = description,
    status = status,
    priority = priority,
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
fun List<TaskEntity>.toDomain() = map { it.toDomain() }


fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    status = status,
    priority = priority,
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
    subtasks = subtasks.map { it.toEntity() }
)

fun SubTask.toEntity() = SubTaskEntity(
    id = id,
    title = title,
    isDone = isDone
)

fun SubTaskEntity.toDomain() = SubTask(
    id = id,
    title = title,
    isDone = isDone
)