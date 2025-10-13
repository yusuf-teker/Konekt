package org.yusufteker.konekt.feature.dashboard

sealed interface DashboardAction {
    object Init : DashboardAction
    object NavigateBack : DashboardAction
    object NavigateToTasks : DashboardAction
    object NavigateToNotes : DashboardAction
    object NavigateToProfile : DashboardAction
    object NavigateToSettings : DashboardAction
}
