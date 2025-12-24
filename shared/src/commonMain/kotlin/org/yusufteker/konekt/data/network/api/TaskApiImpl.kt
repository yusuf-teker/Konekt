package org.yusufteker.konekt.data.network.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.yusufteker.konekt.data.network.baseUrl
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.models.request.CreateTaskRequest
import org.yusufteker.konekt.util.*

class TaskApiImpl(private val client: HttpClient) : TaskApi {


    // 🔑 token tüm isteklerde gerekiyor
    override suspend fun getTasks(token: String): Result<List<Task>, DataError.Remote> =
        safeCall {
            client.get("$baseUrl/tasks") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    override suspend fun addTask(token: String, createTaskRequest: CreateTaskRequest): Result<Task, DataError.Remote> =
        safeCall {
            client.post("$baseUrl/tasks") {
                header(HttpHeaders.Authorization, "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(createTaskRequest)
            }
        }


    override suspend fun updateTask(token: String, task: Task): Result<Task, DataError.Remote> =
        safeCall {
            client.put("$baseUrl/tasks/${task.id}") {
                header(HttpHeaders.Authorization, "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(task)
            }
        }

    override suspend fun deleteTask(token: String, taskId: String): EmptyResult<DataError.Remote> =
        safeCall<Unit> {
            client.delete("$baseUrl/tasks/$taskId") {
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }.asEmptyDataResult()
}
