package org.yusufteker.konekt.data.datasource.remote

import org.yusufteker.konekt.data.network.api.TaskApi
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.util.DataError
import org.yusufteker.konekt.util.Result

class TaskRemoteDataSourceImpl(
    private val api: TaskApi
) : TaskRemoteDataSource {

    override suspend fun fetchTasks(): Result<List<Task>, DataError.Remote> {
        return api.getTasks()
    }

    override suspend fun addTask(task: Task): Result<Task,DataError.Remote> {
        return api.addTask(task)
    }

    override suspend fun updateTask(task: Task): Result<Task, DataError.Remote> {
        return api.updateTask(task)
    }

    override suspend fun deleteTask(taskId: String): Result<Unit, DataError.Remote> {
        return api.deleteTask(taskId)
    }
}