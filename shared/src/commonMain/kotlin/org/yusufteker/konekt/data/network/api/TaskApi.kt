package org.yusufteker.konekt.data.network.api


import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.models.request.CreateTaskRequest
import org.yusufteker.konekt.util.DataError
import org.yusufteker.konekt.util.EmptyResult
import org.yusufteker.konekt.util.Result

interface TaskApi {
    suspend fun getTasks(token: String): Result<List<Task>, DataError.Remote>
    suspend fun addTask(token: String, createTaskRequest: CreateTaskRequest): Result<Task, DataError.Remote>
    suspend fun updateTask(token: String, task: Task): Result<Task, DataError.Remote>
    suspend fun deleteTask(token: String, taskId: String): EmptyResult<DataError.Remote>
}
