package org.yusufteker.konekt.data.datasource.remote

import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.util.DataError
import org.yusufteker.konekt.util.Result
interface TaskRemoteDataSource {
    suspend fun fetchTasks(): Result<List<Task>, DataError.Remote>
    suspend fun addTask(task: Task): Result<Task, DataError.Remote>
    suspend fun updateTask(task: Task): Result<Task, DataError.Remote>
    suspend fun deleteTask(taskId: String): Result<Unit, DataError.Remote>
}
