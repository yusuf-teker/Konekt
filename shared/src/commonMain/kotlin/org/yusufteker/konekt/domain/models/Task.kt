package org.yusufteker.konekt.domain.models

import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Serializable
data class Task @OptIn(ExperimentalTime::class) constructor(
    val id: String,                // UUID gibi benzersiz kimlik
    val title: String,             // Görev başlığı
    val description: String = "",  // Opsiyonel detay açıklama
    val isCompleted: Boolean = false, // Durum
    val dueDate: Long? = null,     // Timestamp (deadline)
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(), // Oluşturulma zamanı
    val updatedAt: Long = createdAt, // Son güncellenme zamanı
    val tags: List<String> = emptyList(), // Etiketler (kategori vs)
    val priority: Int = 0           // Öncelik (0 = normal, 1 = yüksek)
)
