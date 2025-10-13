package org.yusufteker.konekt.feature.tasklist

import org.yusufteker.konekt.base.BaseState
import org.yusufteker.konekt.domain.models.Task

data class TaskListState(
    val tasks: List<Task> = emptyList(),
    override val isLoading: Boolean = false,
    val errorMessage: String? = null

) : BaseState {
    override fun copyWithLoading(isLoading: Boolean) = copy(isLoading = isLoading)
}