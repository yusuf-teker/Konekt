package org.yusufteker.konekt.ui.screen.tasklist.presentation

import org.yusufteker.konekt.ui.base.PlatformBaseViewModel

class TaskListViewModel : PlatformBaseViewModel<TaskListState>(TaskListState()) {

    fun onAction(action: TaskListAction) {
        when (action) {
            is TaskListAction.Init -> {
                // TODO: İlk veri yükleme
            }
            is TaskListAction.NavigateBack -> {
                navigateBack()
            }
        }
    }
}
