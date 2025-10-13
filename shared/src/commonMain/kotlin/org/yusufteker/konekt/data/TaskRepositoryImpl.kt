package org.yusufteker.konekt.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.repository.TaskRepository

class TaskRepositoryImpl : TaskRepository {

    private val _tasks = MutableStateFlow<List<Task>>( // todo yusuf mock veri silinecek
        listOf(
            Task("1", "Projeyi Planla", "Konekt modül yapısını oluştur", false),
            Task("2", "UI Katmanını Yaz", "TaskList ekranını Compose ile yap", false),
            Task("3", "Repository Yapısını Kur", "Veri katmanına geçiş yap", true)
        )
    )
    override fun getTasks(): StateFlow<List<Task>> = _tasks.asStateFlow()

    override suspend fun addTask(task: Task) {
        _tasks.update { current -> current + task }
    }

    override suspend fun updateTask(task: Task) {
        _tasks.update { current -> current.map { if (it.id == task.id) task else it } }
    }

    override suspend fun deleteTask(taskId: String) {
        _tasks.update { current -> current.filterNot { it.id == taskId } }
    }
}
