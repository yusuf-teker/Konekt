package org.yusufteker.konekt.data.local.database

expect class DatabaseFactory {
    fun createDatabase(): AppDatabase
}
const val DB_NAME = "konekt.db"