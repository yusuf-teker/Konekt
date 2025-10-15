package org.yusufteker.konekt.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.yusufteker.konekt.data.network.TaskApi
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.repository.TaskRepository

class TaskRepositoryImpl(private val api: TaskApi) : TaskRepository {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    override fun getTasks(): StateFlow<List<Task>> = _tasks.asStateFlow()

    override suspend fun fetchTasks(): List<Task> {
        return try {
            val fetched = api.getTasks()
            _tasks.value = fetched
            fetched
        } catch (e: Exception) {
            _tasks.value // Eğer hata olursa eski değer dönsün
        }
    }

    override suspend fun addTask(task: Task) {
        try {
            val created = api.addTask(task) // POST backend
            _tasks.update { current -> current + created }
        } catch (e: Exception) {
            _tasks.update { current -> current + task } // offline fallback
        }
    }

    override suspend fun updateTask(task: Task) {
        try {
            val updated = api.updateTask(task)
            _tasks.update { current -> current.map { if (it.id == task.id) updated else it } }
        } catch (e: Exception) {
            _tasks.update { current -> current.map { if (it.id == task.id) task else it } }
        }
    }

    override suspend fun deleteTask(taskId: String) {
        try {
            api.deleteTask(taskId)
            _tasks.update { current -> current.filterNot { it.id == taskId } }
        } catch (e: Exception) {
            _tasks.update { current -> current.filterNot { it.id == taskId } }
        }
    }
}
