package org.yusufteker.konekt.data.repository

import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.yusufteker.konekt.data.entity.TaskEntity
import org.yusufteker.konekt.data.table.TaskTable
import org.yusufteker.konekt.domain.models.TaskPriority
import org.yusufteker.konekt.domain.models.TaskStatus

object TaskRepository {

    fun getTasksByUser(userId: String): List<TaskEntity> = transaction {
        TaskTable.select { TaskTable.createdBy eq userId }
            .map { row ->
                TaskEntity(
                    id = row[TaskTable.id],
                    title = row[TaskTable.title],
                    description = row[TaskTable.description],
                    status = TaskStatus.valueOf(row[TaskTable.status]),
                    priority = TaskPriority.valueOf(row[TaskTable.priority]),
                    createdAt = row[TaskTable.createdAt],
                    updatedAt = row[TaskTable.updatedAt],
                    dueDate = row[TaskTable.dueDate],
                    assignedTo = row[TaskTable.assignedTo],
                    createdBy = row[TaskTable.createdBy],
                    isSynced = row[TaskTable.isSynced],
                    // ✅ JSON'dan decode
                    tags = Json.decodeFromString(row[TaskTable.tags]),
                    commentsCount = row[TaskTable.commentsCount]
                )
            }
    }

    fun insertTask(task: TaskEntity) = transaction {
        TaskTable.insert {
            it[id] = task.id
            it[title] = task.title
            it[description] = task.description
            it[status] = task.status.name
            it[priority] = task.priority.name
            it[createdAt] = task.createdAt
            it[updatedAt] = task.updatedAt
            it[dueDate] = task.dueDate
            it[assignedTo] = task.assignedTo
            it[createdBy] = task.createdBy ?: error("Task must have a creator")
            it[isSynced] = task.isSynced
            it[commentsCount] = task.commentsCount
            // ✅ Listeyi JSON string olarak kaydet
            it[tags] = Json.encodeToString(task.tags)
        }
    }
}
