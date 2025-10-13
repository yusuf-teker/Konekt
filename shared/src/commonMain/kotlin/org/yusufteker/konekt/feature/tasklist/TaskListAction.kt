package org.yusufteker.konekt.feature.tasklist

import org.yusufteker.konekt.domain.models.Task

sealed interface TaskListAction {
    object Init : TaskListAction
    object NavigateBack : TaskListAction
    data object Refresh : TaskListAction
    data class DeleteTask(val id: String) : TaskListAction
    data class ToggleComplete(val id: String, val isCompleted: Boolean) : TaskListAction
    data class  AddTask(val task: Task) : TaskListAction

}