package org.yusufteker.konekt.feature.dashboard

import org.yusufteker.konekt.base.BaseState

data class DashboardState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val dailyMessage: String = "Bugün harika bir gün!",
) : BaseState {
    override fun copyWithLoading(isLoading: Boolean) = copy(isLoading = isLoading)
}
