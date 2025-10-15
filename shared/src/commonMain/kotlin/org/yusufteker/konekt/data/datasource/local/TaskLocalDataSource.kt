package org.yusufteker.konekt.data.datasource.local

import org.yusufteker.konekt.domain.models.Task

interface TaskLocalDataSource {
    suspend fun getAllTasks(): List<Task>
    suspend fun insertTask(task: Task): Boolean
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(id: String): Boolean
    suspend fun replaceAll(tasks: List<Task>): Boolean
}