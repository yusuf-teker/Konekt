package org.yusufteker.konekt.data

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.yusufteker.konekt.data.table.TaskTable
import org.yusufteker.konekt.data.table.UserTable

object DatabaseFactory {

    fun init() {
        // tabloyu oluştur
        transaction {
            SchemaUtils.create(UserTable)
            SchemaUtils.create(TaskTable)
        }
    }
}
