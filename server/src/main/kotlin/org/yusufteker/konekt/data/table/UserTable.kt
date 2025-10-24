package org.yusufteker.konekt.data.table

import org.jetbrains.exposed.sql.Table

object UserTable : Table("users") {

    // 🔑 Kullanıcının benzersiz ID'si (UUID veya özel string)
    val id = varchar("id", 50)
    override val primaryKey = PrimaryKey(id)

    // 👤 Kullanıcının görünen kullanıcı adı (benzersiz olmalı)
    val username = varchar("username", 50).uniqueIndex()

    // 📧 Kullanıcının e-posta adresi (benzersiz)
    val email = varchar("email", 100).uniqueIndex()

    // 🔒 Kullanıcının şifresinin hashlenmiş hali (örnek: bcrypt)
    // Hash uzun olabileceği için 255 karakterlik alan ayrıldı
    val password = varchar("password", 255)

    // 🖼️ Profil fotoğrafı URL’si (opsiyonel alan)
    val avatarUrl = varchar("avatar_url", 255).nullable()

    // 🕒 Kullanıcının oluşturulma zamanı (timestamp - milis cinsinden)
    val createdAt = long("created_at")

    // 🕓 Son güncellenme zamanı (timestamp - milis cinsinden)
    val updatedAt = long("updated_at")

    // ✅ Kullanıcı hesabı aktif mi? (soft delete veya ban durumu için)
    val isActive = bool("is_active").default(true)
}
