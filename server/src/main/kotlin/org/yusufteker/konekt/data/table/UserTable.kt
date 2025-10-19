package org.yusufteker.konekt.data.table

import org.jetbrains.exposed.sql.Table

object UserTable : Table("users") {
    val id = varchar("id", 50)
    override val primaryKey = PrimaryKey(id)

    val username = varchar("username", 50).uniqueIndex()
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 255) // bcrypt hash uzun olur, o yüzden 255 karakter
    val avatarUrl = varchar("avatar_url", 255).nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
    val isActive = bool("is_active").default(true)
}
