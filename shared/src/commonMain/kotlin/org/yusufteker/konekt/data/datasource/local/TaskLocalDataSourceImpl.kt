package org.yusufteker.konekt.data.datasource.local


import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.yusufteker.konekt.domain.models.Task

/**
 * Geçici (RAM tabanlı) local data source.
 * SQLDelight geldiğinde kalıcı hale gelecek.
 */
class TaskLocalDataSourceImpl : TaskLocalDataSource {

    private val tasks = mutableListOf<Task>()
    private val lock = Mutex() // thread safety için

    override suspend fun getAllTasks(): List<Task> = lock.withLock {
        tasks.toList()
    }

    override suspend fun insertTask(task: Task) = lock.withLock {
        tasks.add(task)
    }

    override suspend fun updateTask(task: Task) = lock.withLock {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) tasks[index] = task
    }

    override suspend fun deleteTask(id: String) = lock.withLock {
        tasks.removeAll { it.id == id }
    }

    override suspend fun replaceAll(newTasks: List<Task>) = lock.withLock {
        tasks.clear()
        tasks.addAll(newTasks)
    }
}
