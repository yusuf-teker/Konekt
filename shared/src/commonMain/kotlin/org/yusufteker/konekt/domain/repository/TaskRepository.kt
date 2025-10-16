package org.yusufteker.konekt.domain.repository

import kotlinx.coroutines.flow.Flow
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.util.DataError
import org.yusufteker.konekt.util.Result

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>

    suspend fun addTask(task: Task): Result<Task, DataError.Remote>
    suspend fun updateTask(task: Task): Result<Task, DataError.Remote>
    suspend fun deleteTask(taskId: String): Result<Unit, DataError.Remote>
    suspend fun fetchTasks(): Result<List<Task>, DataError.Remote>
}

