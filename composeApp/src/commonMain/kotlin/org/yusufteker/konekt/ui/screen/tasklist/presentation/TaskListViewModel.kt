package org.yusufteker.konekt.ui.screen.tasklist.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import org.yusufteker.konekt.domain.models.Task
import org.yusufteker.konekt.domain.repository.TaskRepository
import org.yusufteker.konekt.feature.tasklist.TaskListAction
import org.yusufteker.konekt.feature.tasklist.TaskListState
import org.yusufteker.konekt.ui.base.PlatformBaseViewModel

class TaskListViewModel(
    private val taskRepository: TaskRepository
) : PlatformBaseViewModel<TaskListState>(TaskListState()) {

    fun onAction(action: TaskListAction) {
        when (action) {
            is TaskListAction.Init -> loadTasks()
            is TaskListAction.NavigateBack -> navigateBack()
            is TaskListAction.AddTask -> addTask(action.task)
            is TaskListAction.DeleteTask -> deleteTask(action.id)
            is TaskListAction.ToggleComplete -> toggleComplete(action.id, action.isCompleted)
            is TaskListAction.Refresh -> refreshTasks()
        }
    }


    private fun loadTasks() {
        launchWithLoading {
            taskRepository.getTasks().onEach { tasks ->
                setState { copy(tasks = tasks) }

            }.launchIn(viewModelScope)
        }
    }
    private fun addTask(task: Task) {
        launchWithLoading {
            taskRepository.addTask(task)
        }
    }

    private fun refreshTasks() {
        launchWithLoading {
            setState { copy(tasks = tasks.shuffled()) }
        }
    }
    private fun toggleComplete(id: String, isCompleted: Boolean) {
        launchWithLoading {
            val task = state.value.tasks.find { it.id == id } ?: return@launchWithLoading
            taskRepository.updateTask(task.copy(isCompleted = isCompleted))
        }
    }
    private fun deleteTask(id: String) {
        launchWithLoading {
            setState { copy(tasks = tasks.filterNot { it.id == id }) }
        }
    }
}
