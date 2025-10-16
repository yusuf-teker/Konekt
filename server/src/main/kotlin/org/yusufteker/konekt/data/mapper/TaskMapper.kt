package org.yusufteker.konekt.data.mapper


import org.yusufteker.konekt.data.entity.TaskEntity
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.models.TaskPriority
import org.yusufteker.konekt.domain.models.TaskStatus

fun TaskEntity.toDomain(): Task = Task(
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
    isSynced = isSynced,
    tags = tags,
    commentsCount = commentsCount
)

fun Task.toEntity(): TaskEntity = TaskEntity(
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
    isSynced = isSynced,
    tags = tags,
    commentsCount = commentsCount
)

/*Client + Serverde api resposne için Task modeli kullanılcak

fun TaskDTO.toDomain(): Task = Task(
    id = id,
    title = title,
    description = description,
    status = TaskStatus.valueOf(status),
    priority = TaskPriority.valueOf(priority),
    createdAt = createdAt,
    updatedAt = updatedAt,
    dueDate = dueDate,
    assignedTo = assignedTo,
    createdBy = createdBy,
    isSynced = isSynced,
    tags = tags,
    commentsCount = commentsCount
)

fun Task.toDTO(): TaskDTO = TaskDTO(
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
    isSynced = isSynced,
    tags = tags,
    commentsCount = commentsCount
)
*/