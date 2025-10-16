package org.yusufteker.konekt.data.table

import org.jetbrains.exposed.sql.Table
/*Bu bir ORM'DİR(Exposed)
veritabanındaki tabloyu tanımlar.

Exposed bu tablo tanımını kullanarak:

tabloyu oluşturur (migration)

sorguları type-safe şekilde çalıştırır
* */
object UserTable : Table("users") {
    val id = varchar("id", 50)
    override val primaryKey = PrimaryKey(id)

    val username = varchar("username", 50).uniqueIndex()
    val email = varchar("email", 100).uniqueIndex()
    val avatarUrl = varchar("avatar_url", 255).nullable()
    val createdAt = long("created_at")
    val updatedAt = long("updated_at")
    val isActive = bool("is_active").default(true)
}