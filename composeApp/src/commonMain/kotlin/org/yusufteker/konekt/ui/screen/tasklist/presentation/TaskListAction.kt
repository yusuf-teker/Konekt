package org.yusufteker.konekt.ui.screen.tasklist.presentation


sealed interface TaskListAction {
    object Init : TaskListAction
    object NavigateBack : TaskListAction
}
