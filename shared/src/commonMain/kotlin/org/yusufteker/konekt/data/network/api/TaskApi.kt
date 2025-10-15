package org.yusufteker.konekt.data.network.api


import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.util.DataError
import org.yusufteker.konekt.util.Result

interface TaskApi {
    suspend fun getTasks(): Result<List<Task>, DataError.Remote>
    suspend fun addTask(task: Task): Result<Task, DataError.Remote>
    suspend fun updateTask(task: Task):Result<Task, DataError.Remote>
    suspend fun deleteTask(taskId: String):Result<Unit, DataError.Remote>
}
