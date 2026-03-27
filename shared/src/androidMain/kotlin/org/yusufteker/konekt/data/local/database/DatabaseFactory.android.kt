package org.yusufteker.konekt.data.local.database

import android.content.Context
import androidx.room.Room

actual class DatabaseFactory(private val context: Context) {
    actual fun createDatabase(): AppDatabase {
        val dbFile = context.getDatabasePath(DB_NAME)
        return Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath
        ).build()
    }
}

