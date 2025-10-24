package org.yusufteker.konekt.data.repository

import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.yusufteker.konekt.data.datasource.local.TaskLocalDataSource
import org.yusufteker.konekt.data.datasource.remote.TaskRemoteDataSource
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.repository.TaskRepository
import org.yusufteker.konekt.util.*

class TaskRepositoryImpl(
    private val remote: TaskRemoteDataSource,
    private val local: TaskLocalDataSource // Todo: local daha sonra ayarlanacak
) : TaskRepository {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    override fun getTasks(): StateFlow<List<Task>> = _tasks.asStateFlow()

    // ------------------- FETCH TASKS -------------------
    override suspend fun fetchTasks(): Result<List<Task>, DataError.Remote> {
        return when (val result = remote.fetchTasks()) {
            is Result.Success -> {
                result.onSuccess { fetched ->
                    // TODO: local.replaceAll(fetched)
                    _tasks.value = fetched
                }
                result
            }

            is Result.Error -> {
                Napier.e("Fetch tasks failed: $result")
                result.onError { error ->
                    Napier.e("Fetch tasks failed: $error")
                    // Offline fallback
                    // TODO: _tasks.value = local.getAllTasks()
                }
                result
            }

        }
    }

    // ------------------- ADD TASK -------------------
    override suspend fun addTask(task: Task): Result<Task, DataError.Remote> {
        return when (val result = remote.addTask(task)) {
            is Result.Success -> {
                result.onSuccess { created ->
                    // TODO: local.insertTask(created)
                    _tasks.update { it + created }
                }
                result
            }

            is Result.Error -> {
                Napier.e("Add task failed: $result")
                result.onError { error ->
                    Napier.e("Add task failed: $error")
                    // Offline fallback
                    // TODO: local.insertTask(task)
                    //_tasks.update { it + task }
                }
                result
            }

        }
    }

    // ------------------- UPDATE TASK -------------------
    override suspend fun updateTask(task: Task): Result<Task, DataError.Remote> {
        return when (val result = remote.updateTask(task)) {
            is Result.Success -> {
                result.onSuccess { updated ->
                    // TODO: local.updateTask(updated)
                    _tasks.update { current ->
                        current.map { if (it.id == task.id) updated else it }
                    }
                }
                result
            }

            is Result.Error -> {
                Napier.e("Update task failed: $result")
                result.onError { error ->
                    Napier.e("Update task failed: $error")
                    // Offline fallback
                    // TODO: local.updateTask(task)
                }
                result
            }

        }
    }

    // ------------------- DELETE TASK -------------------
    override suspend fun deleteTask(taskId: String): Result<Unit, DataError.Remote> {
        return when (val result = remote.deleteTask(taskId)) {
            is Result.Success -> {
                result.onSuccess {
                    // TODO: local.deleteTask(taskId)
                    _tasks.update { current -> current.filterNot { it.id == taskId } }
                }
                result
            }

            is Result.Error -> {
                Napier.e("Delete task failed: $result")
                result.onError { error ->
                    Napier.e("Delete task failed: $error")
                    // Offline fallback
                    // TODO: local.deleteTask(taskId)
                    //_tasks.update { current -> current.filterNot { it.id == taskId } }
                }
                result
            }

        }
    }
}
