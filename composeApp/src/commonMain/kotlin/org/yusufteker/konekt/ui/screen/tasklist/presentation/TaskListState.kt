package org.yusufteker.konekt.ui.screen.tasklist.presentation

import org.yusufteker.konekt.base.BaseState

data class TaskListState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null
) : BaseState {
    override fun copyWithLoading(isLoading: Boolean): BaseState {
        return this.copy(isLoading = isLoading)
    }
}
