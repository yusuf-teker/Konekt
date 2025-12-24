package org.yusufteker.konekt.data.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.yusufteker.konekt.domain.models.TaskPriority
import org.yusufteker.konekt.domain.models.TaskStatus

/**
 * TaskTable — Görevlerin tutulduğu ana tablo.
 * Exposed ORM kullanılarak PostgreSQL üzerinde oluşturulur.
 */
object TaskTable : Table("tasks") {

    // 🔑 --- Birincil Anahtar ---
    val id = varchar("id", 50)
    override val primaryKey = PrimaryKey(id)

    // 🏷️ --- Temel Bilgiler ---
    val title = varchar("title", 255)
    val description = text("description").nullable()

    // 📊 --- Durum ve Öncelik ---
    val status = varchar("status", 50)
        .default(TaskStatus.TODO.name)
    val priority = varchar("priority", 50)
        .default(TaskPriority.MEDIUM.name)

    // 🕒 --- Tarih Bilgileri ---
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
    val dueDate = long("due_date").nullable()

    // 🔔 --- Hatırlatıcı Bilgileri (YENİ) ---
    val reminderTime = long("reminder_time").nullable()
    // Hatırlatıcı zamanı (epoch millis)

    val isReminderSent = bool("is_reminder_sent")
        .default(false)
    // Hatırlatıcı gönderildi mi?

    // 🔁 --- Tekrarlayan Görev Bilgileri (YENİ) ---
    val isRecurring = bool("is_recurring")
        .default(false)
    // Bu görev tekrarlayan bir görev mi?

    val recurrencePattern = varchar("recurrence_pattern", 50).nullable()
    // Tekrar deseni: DAILY, WEEKLY, MONTHLY, YEARLY, CUSTOM

    val recurrenceConfig = text("recurrence_config").nullable()
    // Tekrar konfigürasyonu (JSON)
    // Örn: {"interval":2,"endDate":1234567890,"daysOfWeek":[1,3,5]}

    // 👤 --- Kullanıcı İlişkileri ---
    val assignedTo = varchar("assigned_to", 50)
        .references(UserTable.id, onDelete = ReferenceOption.SET_NULL)
        .nullable()

    val createdBy = varchar("created_by", 50)
        .references(UserTable.id, onDelete = ReferenceOption.CASCADE)

    val updatedBy = varchar("updated_by", 50)
        .references(UserTable.id, onDelete = ReferenceOption.SET_NULL)
        .nullable()

    // 📦 --- Senkronizasyon ve Durum Bilgileri ---
    val isSynced = bool("is_synced")
        .default(true)

    val isArchived = bool("is_archived")
        .default(false)

    // 🏷️ --- Ek Özellikler ---
    val tags = text("tags")
        .default("[]")

    val attachments = text("attachments")
        .default("[]")

    val subtasks = text("subtasks")
        .default("[]")

    val commentsCount = integer("comments_count")
        .default(0)

    // 🎨 --- Görsel Özellikler (Opsiyonel) ---
    val colorTag = varchar("color_tag", 7).nullable()
    // Hex color: #FF5722

    val viewsCount = integer("views_count")
        .default(0)
    // Görevin görüntülenme sayısı

    val completionRate = integer("completion_rate")
        .default(0)
    // Alt görev tamamlanma yüzdesi (0-100)

    // 📍 --- Konum Bilgileri ---
    val location = varchar("location", 255).nullable()
    val latitude = double("latitude").nullable()
    val longitude = double("longitude").nullable()

    // 📈 --- İndeksler ---
    init {
        index(false, createdBy)
        index(false, assignedTo)
        index(false, status)
        index(false, createdAt)
        index(false, isArchived)
        index(false, reminderTime) // ✅ Hatırlatıcı sorguları için
        index(false, isRecurring)  // ✅ Tekrarlayan görev sorguları için
    }
}