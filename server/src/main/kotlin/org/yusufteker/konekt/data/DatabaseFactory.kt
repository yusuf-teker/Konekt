package org.yusufteker.konekt.data

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.yusufteker.konekt.data.table.UserTable

object DatabaseFactory {

    fun init() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/konekt",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "password" // kendi şifreni yaz
        )

        // tabloyu oluştur
        transaction {
            SchemaUtils.create(UserTable)
        }
    }
}
