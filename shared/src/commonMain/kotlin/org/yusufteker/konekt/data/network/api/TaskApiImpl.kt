package org.yusufteker.konekt.data.network.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.util.*

class TaskApiImpl(private val client: HttpClient) : TaskApi {

    private val baseUrl = "http://10.0.2.2:8080"

    override suspend fun getTasks(): Result<List<Task>, DataError.Remote> =
        safeCall {
            client.get("$baseUrl/tasks")
        }

    override suspend fun addTask(task: Task): Result<Task, DataError.Remote> =
        safeCall {
            client.post("$baseUrl/task") {
                contentType(ContentType.Application.Json)
                setBody(task)
            }
        }

    override suspend fun updateTask(task: Task): Result<Task, DataError.Remote> =
        safeCall {
            client.put("$baseUrl/tasks/${task.id}") {
                contentType(ContentType.Application.Json)
                setBody(task)
            }
        }

    override suspend fun deleteTask(taskId: String): EmptyResult<DataError.Remote> =
        safeCall<Unit> {
            client.delete("$baseUrl/task/$taskId")
        }.asEmptyDataResult()
}
