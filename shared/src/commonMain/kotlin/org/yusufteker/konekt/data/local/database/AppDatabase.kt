package org.yusufteker.konekt.data.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.yusufteker.konekt.data.local.RoomConverters
import org.yusufteker.konekt.data.local.dao.TaskDao
import org.yusufteker.konekt.data.local.entities.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
@TypeConverters(RoomConverters::class)
@ConstructedBy(AppDatabaseConstructor::class)// actual olmadan otomatik olusturur
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}