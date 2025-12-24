package org.yusufteker.konekt.data.repository

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.yusufteker.konekt.data.entity.SubTaskEntity
import org.yusufteker.konekt.data.entity.TaskEntity
import org.yusufteker.konekt.data.table.TaskTable
import java.util.UUID
import kotlinx.serialization.json.Json
import org.yusufteker.konekt.domain.models.request.CreateSubTaskRequest

class TaskRepository {

    val json = Json { ignoreUnknownKeys = true }

    /**
     * ✅ Yeni görev oluşturur.
     * createdBy: Auth olmuş kullanıcıdan alınır.
     */
    fun createTask(
        title: String,
        description: String?,
        status: String,
        priority: String,
        dueDate: Long?,
        reminderTime: Long?,
        isRecurring: Boolean,
        recurrencePattern: String?,
        recurrenceConfigJson: String?,
        colorTag: String?,
        tags: List<String>,
        attachments: List<String>,
        subtasks: List<CreateSubTaskRequest>,
        createdBy: String
    ): TaskEntity? = transaction {
        val id = UUID.randomUUID().toString()
        val now = System.currentTimeMillis()

        val inserted = TaskTable.insert {
            it[TaskTable.id] = id
            it[TaskTable.title] = title
            it[TaskTable.description] = description
            it[TaskTable.status] = status
            it[TaskTable.priority] = priority
            it[TaskTable.dueDate] = dueDate
            it[TaskTable.reminderTime] = reminderTime
            it[TaskTable.isReminderSent] = false

            it[TaskTable.isRecurring] = isRecurring
            it[TaskTable.recurrencePattern] = recurrencePattern
            it[TaskTable.recurrenceConfig] = recurrenceConfigJson

            it[TaskTable.colorTag] = colorTag

            // 🏷️ Koleksiyonları JSON olarak kaydet
            it[TaskTable.tags] = json.encodeToString(tags)
            it[TaskTable.attachments] = json.encodeToString(attachments)
            val subtaskEntities = subtasks.map { sub ->
                SubTaskEntity(
                    id = UUID.randomUUID().toString(),
                    title = sub.title,
                    isDone = sub.isDone,
                    order = sub.order
                )
            }
            it[TaskTable.subtasks] = json.encodeToString<List<SubTaskEntity>>(subtaskEntities)

            // 🧭 Konum (eklemek istersen)
            it[TaskTable.latitude] = null
            it[TaskTable.longitude] = null
            it[TaskTable.location] = null

            it[TaskTable.createdAt] = now
            it[TaskTable.updatedAt] = now
            it[TaskTable.createdBy] = createdBy
            it[TaskTable.updatedBy] = createdBy

            it[TaskTable.isSynced] = true
            it[TaskTable.isArchived] = false

            it[TaskTable.commentsCount] = 0
            it[TaskTable.viewsCount] = 0
            it[TaskTable.completionRate] = 0
        }.resultedValues?.singleOrNull()

        inserted?.toTaskEntity()
    }


    /**
     * 🔍 Belirli bir kullanıcıya ait görevleri listeler.
     */
    fun getTasksByUser(userId: String): List<TaskEntity> = transaction {
        TaskTable
            .select { TaskTable.createdBy eq userId and (TaskTable.isArchived eq false) }
            .orderBy(TaskTable.createdAt to SortOrder.DESC)
            .map { it.toTaskEntity() }
    }

    /**
     * 🔍 ID'ye göre görev bulur.
     */
    fun getTaskById(taskId: String): TaskEntity? = transaction {
        TaskTable.select { TaskTable.id eq taskId }
            .map { it.toTaskEntity() }
            .singleOrNull()
    }

    /**
     * ✏️ Görev günceller.
     */
    fun updateTask(taskId: String, title: String?, description: String?, status: String?, priority: String?, updatedBy: String): Boolean =
        transaction {
            val now = System.currentTimeMillis()
            val updatedRows = TaskTable.update({ TaskTable.id eq taskId }) {
                if (title != null) it[TaskTable.title] = title
                if (description != null) it[TaskTable.description] = description
                if (status != null) it[TaskTable.status] = status
                if (priority != null) it[TaskTable.priority] = priority
                it[TaskTable.updatedAt] = now
                it[TaskTable.updatedBy] = updatedBy
            }
            updatedRows > 0
        }

    /**
     * 🗑️ Görevi arşivler (soft delete).
     */
    fun archiveTask(taskId: String): Boolean = transaction {
        val updatedRows = TaskTable.update({ TaskTable.id eq taskId }) {
            it[TaskTable.isArchived] = true
        }
        updatedRows > 0
    }

    /**
     * 💬 Alt görev ekleme örneği (isteğe bağlı).
     */
    fun addSubTask(taskId: String, subtask: SubTaskEntity): Boolean = transaction {
        val task = getTaskById(taskId) ?: return@transaction false
        val newSubtasks = task.subtasks.toMutableList().apply { add(subtask) }

        val updatedRows = TaskTable.update({ TaskTable.id eq taskId }) {
            it[TaskTable.subtasks] = json.encodeToString(newSubtasks)
            it[TaskTable.updatedAt] = System.currentTimeMillis()
        }
        updatedRows > 0
    }

    /**
     * 🧹 Tüm görevleri sil (test amaçlı).
     */
    fun clearAll() = transaction {
        TaskTable.deleteAll()
    }


    /**
     * 🧠 ResultRow → TaskEntity dönüşümü (tam sürüm)
     */
    private fun ResultRow.toTaskEntity(): TaskEntity = TaskEntity(
        id = this[TaskTable.id],
        title = this[TaskTable.title],
        description = this[TaskTable.description],

        // Status & Priority
        status = org.yusufteker.konekt.domain.models.TaskStatus.valueOf(this[TaskTable.status]),
        priority = org.yusufteker.konekt.domain.models.TaskPriority.valueOf(this[TaskTable.priority]),

        // Timestamps
        createdAt = this[TaskTable.createdAt],
        updatedAt = this[TaskTable.updatedAt],
        dueDate = this[TaskTable.dueDate],

        // 🔔 Reminder
        reminderTime = this[TaskTable.reminderTime],
        isReminderSent = this[TaskTable.isReminderSent],

        // 🔁 Recurrence
        isRecurring = this[TaskTable.isRecurring],
        recurrencePattern = this[TaskTable.recurrencePattern]?.let {
            org.yusufteker.konekt.domain.models.RecurrencePattern.valueOf(it)
        },
        recurrenceConfig = this[TaskTable.recurrenceConfig]?.let {
            json.decodeFromString<org.yusufteker.konekt.data.entity.RecurrenceConfigEntity>(it)
        },

        // User Relations
        assignedTo = this[TaskTable.assignedTo],
        createdBy = this[TaskTable.createdBy],
        updatedBy = this[TaskTable.updatedBy],

        // Sync & Archive
        isSynced = this[TaskTable.isSynced],
        isArchived = this[TaskTable.isArchived],

        // Collections (JSON string → list)
        tags = json.decodeFromString(this[TaskTable.tags]),
        attachments = json.decodeFromString(this[TaskTable.attachments]),
        subtasks = json.decodeFromString(this[TaskTable.subtasks]),

        // Metadata
        commentsCount = this[TaskTable.commentsCount],
        viewsCount = this[TaskTable.viewsCount],
        completionRate = this[TaskTable.completionRate],

        // 🎨 Visual
        colorTag = this[TaskTable.colorTag]
    )

}
