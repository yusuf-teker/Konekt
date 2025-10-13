package org.yusufteker.konekt.ui.screen.dashboard.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.yusufteker.konekt.domain.repository.MessageRepository
import org.yusufteker.konekt.domain.repository.TaskRepository
import org.yusufteker.konekt.feature.dashboard.DashboardAction
import org.yusufteker.konekt.feature.dashboard.DashboardState
import org.yusufteker.konekt.ui.base.PlatformBaseViewModel
import org.yusufteker.konekt.ui.base.UiEvent
import org.yusufteker.konekt.ui.navigation.Routes

class DashboardViewModel(
    val taskRepository: TaskRepository,
    val messageRepository: MessageRepository
) :
    PlatformBaseViewModel<DashboardState>(DashboardState()) {

    fun onAction(action: DashboardAction) {
        when (action) {
            is DashboardAction.Init -> {}
            is DashboardAction.NavigateBack -> navigateBack()
            is DashboardAction.NavigateToTasks -> sendUiEventSafe(UiEvent.NavigateTo(Routes.TaskListScreen))
            is DashboardAction.NavigateToNotes -> sendUiEventSafe(UiEvent.NavigateTo(Routes.NotesScreen))
            is DashboardAction.NavigateToProfile -> sendUiEventSafe(UiEvent.NavigateTo(Routes.SettingsScreen))
            is DashboardAction.NavigateToSettings -> sendUiEventSafe(UiEvent.NavigateTo(Routes.SettingsScreen))
        }
    }

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        launchWithLoading {
            taskRepository.getTasks()
                .combine(messageRepository.getDailyMessage()) { tasks, message ->
                    DashboardState(
                        totalTasks = tasks.size,
                        completedTasks = tasks.count { it.isCompleted },
                        dailyMessage = message
                    )
                }
                .onEach { setState { it } }
                .launchIn(viewModelScope)
        }
    }

}
