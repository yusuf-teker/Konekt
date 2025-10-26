package org.yusufteker.konekt.data.mapper

import org.yusufteker.konekt.data.entity.*
import org.yusufteker.konekt.domain.models.*

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
    isRecurring = isRecurring,
    recurrencePattern = recurrencePattern,
    recurrenceConfig = recurrenceConfig?.toDomain(),
    reminderTime = reminderTime,
    isReminderSent = isReminderSent,
    isSynced = isSynced,
    isArchived = isArchived,
    commentsCount = commentsCount,
    viewsCount = viewsCount,
    completionRate = completionRate,
    colorTag = colorTag,
    tags = tags,
    attachments = attachments,
    subtasks = subtasks.map { it.toDomain() },
    location = location,
    latitude = latitude,
    longitude = longitude
)

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
    isRecurring = isRecurring,
    recurrencePattern = recurrencePattern,
    recurrenceConfig = recurrenceConfig?.toEntity(),
    reminderTime = reminderTime,
    isReminderSent = isReminderSent,
    isSynced = isSynced,
    isArchived = isArchived,
    commentsCount = commentsCount,
    viewsCount = viewsCount,
    completionRate = completionRate,
    colorTag = colorTag,
    tags = tags,
    attachments = attachments,
    subtasks = subtasks.map { it.toEntity() },
    location = location,
    latitude = latitude,
    longitude = longitude
)



fun SubTaskEntity.toDomain() = SubTask(id, title, isDone, order)
fun SubTask.toEntity() = SubTaskEntity(id, title, isDone, order)

fun RecurrenceConfigEntity.toDomain() =
    RecurrenceConfig(pattern, interval, endDate, daysOfWeek)

fun RecurrenceConfig.toEntity() =
    RecurrenceConfigEntity(pattern, interval, endDate, daysOfWeek)
