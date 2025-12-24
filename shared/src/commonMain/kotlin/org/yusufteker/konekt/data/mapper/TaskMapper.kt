package org.yusufteker.konekt.data.mapper

import org.yusufteker.konekt.data.network.dto.*
import org.yusufteker.konekt.domain.models.*
import org.yusufteker.konekt.domain.models.request.CreateSubTaskRequest
import org.yusufteker.konekt.domain.models.request.CreateTaskRequest
import org.yusufteker.konekt.domain.models.request.RecurrenceConfigRequest

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

    fun Task.toCreateRequest(): CreateTaskRequest {
        return CreateTaskRequest(
            title = title,
            description = description,
            status = status,
            priority = priority,
            dueDate = dueDate,
            reminderTime = reminderTime,
            isReminderSent = isReminderSent,
            isRecurring = isRecurring,
            recurrencePattern = recurrencePattern,
            recurrenceConfig = recurrenceConfig?.toRequest(),
            colorTag = colorTag,
            location = location,
            latitude = latitude,
            longitude = longitude,
            tags = tags,
            attachments = attachments,
            subtasks = subtasks.map { it.toRequest() }
        )
    }

    fun SubTask.toRequest(): CreateSubTaskRequest {
        return CreateSubTaskRequest(
            title = title,
            isDone = isDone,
            order = order
        )
    }

    fun RecurrenceConfig.toRequest(): RecurrenceConfigRequest {
        return RecurrenceConfigRequest(
            pattern = pattern,
            interval = interval,
            endDate = endDate,
            daysOfWeek = daysOfWeek
        )
    }


}
