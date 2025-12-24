package org.yusufteker.konekt.data.datasource.remote

import org.yusufteker.konekt.data.mapper.TaskMapper.toCreateRequest
import org.yusufteker.konekt.data.mapper.TaskMapper.toDTO
import org.yusufteker.konekt.data.network.api.TaskApi
import org.yusufteker.konekt.data.repository.SessionManagerImpl
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.models.request.CreateTaskRequest
import org.yusufteker.konekt.util.DataError
import org.yusufteker.konekt.util.Result

class TaskRemoteDataSourceImpl(
    private val api: TaskApi,
    private val sessionManager: SessionManagerImpl
) : TaskRemoteDataSource {



    override suspend fun fetchTasks(): Result<List<Task>, DataError.Remote> {
        val token = sessionManager.getToken()
            ?: return Result.Error(DataError.Remote.UNAUTHORIZED)

        return api.getTasks(token)
    }

    override suspend fun addTask(task: Task): Result<Task,DataError.Remote> {
        val token = sessionManager.getToken()
            ?: return Result.Error(DataError.Remote.UNAUTHORIZED)

        val createTaskRequest = task.toCreateRequest()
        return api.addTask(token, createTaskRequest)
    }

    override suspend fun updateTask(task: Task): Result<Task, DataError.Remote> {
        val token = sessionManager.getToken()
            ?: return Result.Error(DataError.Remote.UNAUTHORIZED)

        return api.updateTask(token, task)
    }

    override suspend fun deleteTask(taskId: String): Result<Unit, DataError.Remote> {
        val token = sessionManager.getToken()
            ?: return Result.Error(DataError.Remote.UNAUTHORIZED)

        return api.deleteTask(token, taskId)
    }
}