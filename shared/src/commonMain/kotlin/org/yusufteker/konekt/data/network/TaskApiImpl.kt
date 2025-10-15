package org.yusufteker.konekt.data.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.yusufteker.konekt.domain.models.Task

class TaskApiImpl(private val client: HttpClient) : TaskApi {
    private val baseUrl = "http://10.0.2.2:8080" // Todo  yusuf sonradan bakılacak manifestteki clearText ve networking rule kontrol edilecek

    override suspend fun getTasks() = client.get("$baseUrl/tasks").body<List<Task>>()

    override suspend fun addTask(task: Task) = client.post("$baseUrl/task") {
        contentType(ContentType.Application.Json)
        setBody(task)
    }.body<Task>()

    override suspend fun updateTask(task: Task) = client.put("$baseUrl/task/${task.id}") {
        contentType(ContentType.Application.Json)
        setBody(task)
    }.body<Task>()

    override suspend fun deleteTask(taskId: String) {
        client.delete("$baseUrl/task/$taskId")
    }
}