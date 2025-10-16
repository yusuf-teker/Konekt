package org.yusufteker.konekt.data.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.yusufteker.konekt.domain.models.TaskPriority
import org.yusufteker.konekt.domain.models.TaskStatus


object TaskTable : Table("tasks") {
    // Primary Key
    val id = varchar("id", 50)
    override val primaryKey = PrimaryKey(id)

    // Task Bilgileri
    val title = varchar("title", 255)
    val description = text("description").nullable()
    val status = varchar("status", 50).default(TaskStatus.TODO.name)
    val priority = varchar("priority", 50).default(TaskPriority.MEDIUM.name)

    // Timestamps (Long olarak - epoch milliseconds)
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
    val dueDate = long("due_date").nullable()

    // User Relations
    val assignedTo = varchar("assigned_to", 50)
        .references(UserTable.id, onDelete = ReferenceOption.SET_NULL)
        .nullable()  // ← nullable() en sona

    val createdBy = varchar("created_by", 50)
        .references(UserTable.id, onDelete = ReferenceOption.CASCADE)

    // Sync & Metadata
    val isSynced = bool("is_synced").default(true)
    val commentsCount = integer("comments_count").default(0)

    // İndeksler (performans için)
    init {
        index(false, createdBy)
        index(false, assignedTo)
        index(false, status)
        index(false, createdAt)
    }
}



