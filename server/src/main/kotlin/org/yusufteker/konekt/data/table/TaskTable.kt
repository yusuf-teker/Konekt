package org.yusufteker.konekt.data.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.yusufteker.konekt.domain.models.TaskPriority
import org.yusufteker.konekt.domain.models.TaskStatus

/**
 * TaskTable — Görevlerin tutulduğu ana tablo.
 * Exposed ORM kullanılarak PostgreSQL üzerinde oluşturulur.
 *
 * Bu tablo "offline sync", "AI öneri", "çok kullanıcılı görev yönetimi"
 * ve "etiketleme/alt görev" özelliklerine hazırdır.
 */
object TaskTable : Table("tasks") {

    // 🔑 --- Birincil Anahtar ---
    val id = varchar("id", 50) // UUID ya da custom id (örnek: task_123)
    override val primaryKey = PrimaryKey(id)

    // 🏷️ --- Temel Bilgiler ---
    val title = varchar("title", 255) // Görev başlığı
    val description = text("description").nullable() // Açıklama (nullable)

    // 📊 --- Durum ve Öncelik ---
    val status = varchar("status", 50)
        .default(TaskStatus.TODO.name) // Enum'dan string olarak saklanır
    val priority = varchar("priority", 50)
        .default(TaskPriority.MEDIUM.name)

    // 🕒 --- Tarih Bilgileri ---
    val createdAt = long("created_at") // UNIX time (epoch millis)
    val updatedAt = long("updated_at")
    val dueDate = long("due_date").nullable() // Son teslim tarihi

    // 👤 --- Kullanıcı İlişkileri ---
    val assignedTo = varchar("assigned_to", 50)
        .references(UserTable.id, onDelete = ReferenceOption.SET_NULL)
        .nullable()
    // Görev atanmış kişi. Silinirse null yapılır.

    val createdBy = varchar("created_by", 50)
        .references(UserTable.id, onDelete = ReferenceOption.CASCADE)
    // Görevi oluşturan kullanıcı. Silinirse görev de silinir.

    val updatedBy = varchar("updated_by", 50)
        .references(UserTable.id, onDelete = ReferenceOption.SET_NULL)
        .nullable()
    // Son düzenleyen kullanıcı. Silinirse null yapılır.

    // 📦 --- Senkronizasyon ve Durum Bilgileri ---
    val isSynced = bool("is_synced")
        .default(true)
    // Offline-first senkronizasyon için. True => sunucu ile uyumlu.

    val isArchived = bool("is_archived")
        .default(false)
    // Görev arşive taşındı mı? (Soft delete / "Archive" sekmesi)

    // 🏷️ --- Ek Özellikler ---
    val tags = text("tags")
        .default("[]")
    // Görev etiketleri (JSON array formatında tutulur)
    // Örn: ["urgent", "work", "backend"]

    val attachments = text("attachments")
        .default("[]")
    // Göreve ekli dosyalar (JSON array of URLs)
    // Örn: ["https://cdn.app.com/file1.jpg"]

    val subtasks = text("subtasks")
        .default("[]")
    // Alt görevler (JSON array of objects)
    // Örn: [{"id":"sub1","title":"Design UI","isDone":false}]

    val commentsCount = integer("comments_count")
        .default(0)
    // Görev altındaki yorum sayısı (cache amaçlı)

    // 📈 --- İndeksler ---
    init {
        index(false, createdBy) // kullanıcıya göre filtreleme
        index(false, assignedTo) // atanmış kullanıcıya göre filtreleme
        index(false, status) // durum bazlı filtreleme (örnek: sadece DONE)
        index(false, createdAt) // tarih bazlı sıralama
        index(false, isArchived) // arşivlenmiş / aktif görev ayrımı
    }
}
