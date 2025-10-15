package org.yusufteker.konekt.data.repository

import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.yusufteker.konekt.data.datasource.local.TaskLocalDataSource
import org.yusufteker.konekt.data.datasource.remote.TaskRemoteDataSource
import org.yusufteker.konekt.data.network.api.TaskApi
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.repository.TaskRepository
import org.yusufteker.konekt.util.*

class TaskRepositoryImpl(
    private val remote: TaskRemoteDataSource,
    private val local: TaskLocalDataSource
) : TaskRepository {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    override fun getTasks(): StateFlow<List<Task>> = _tasks.asStateFlow()

    override suspend fun fetchTasks(): List<Task> {
        val result = remote.fetchTasks()

        result.onSuccess { fetched ->
            _tasks.value = fetched
        }.onError { error ->
            Napier.e("Fetch tasks failed: $error")
        }

        return when (result) {
            is Result.Success -> result.data // apiden değer gelirse güncelle
            is Result.Error -> _tasks.value // gelmez ise eskisi kalmaya devam edebilir
        }
    }

    override suspend fun addTask(task: Task) {
        val result = remote.addTask(task)

        result.onSuccess { created ->
            _tasks.update { current -> current + created }
        }.onError { error ->
            println("Add task failed: $error")
            // Offline fallback: Local olarak eklemeye devam et
            _tasks.update { current -> current + task }
        }
    }

    override suspend fun updateTask(task: Task) {
        val result = remote.updateTask(task)

        result.onSuccess { updated ->
            _tasks.update { current ->
                current.map { if (it.id == task.id) updated else it }
            }
        }.onError { error ->
            println("Update task failed: $error")
            // Offline fallback
            _tasks.update { current ->
                current.map { if (it.id == task.id) task else it }
            }
        }
    }

    override suspend fun deleteTask(taskId: String) {
        val result = remote.deleteTask(taskId)

        result.onSuccess {
            _tasks.update { current -> current.filterNot { it.id == taskId } }
        }.onError { error ->
            println("Delete task failed: $error")
            // Offline fallback (zaten silinmiş gibi davranıyoruz)
            _tasks.update { current -> current.filterNot { it.id == taskId } }
        }
    }
}
