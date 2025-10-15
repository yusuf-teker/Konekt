package org.yusufteker.konekt.data.network


import org.yusufteker.konekt.domain.models.Task

interface TaskApi {
    suspend fun getTasks(): List<Task>
    suspend fun addTask(task: Task): Task
    suspend fun updateTask(task: Task): Task
    suspend fun deleteTask(taskId: String)
}
