package org.yusufteker.konekt.ui.screen.dashboard.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.yusufteker.konekt.data.repository.AuthRepositoryImpl
import org.yusufteker.konekt.domain.models.TaskStatus
import org.yusufteker.konekt.domain.repository.AuthRepository
import org.yusufteker.konekt.domain.repository.MessageRepository
import org.yusufteker.konekt.domain.repository.TaskRepository
import org.yusufteker.konekt.feature.dashboard.DashboardAction
import org.yusufteker.konekt.feature.dashboard.DashboardState
import org.yusufteker.konekt.ui.base.PlatformBaseViewModel
import org.yusufteker.konekt.ui.base.UiEvent
import org.yusufteker.konekt.ui.navigation.Routes
import org.yusufteker.konekt.util.security.AuthEvent
import org.yusufteker.konekt.util.security.AuthEventBus

class DashboardViewModel(
    val taskRepository: TaskRepository,
    val messageRepository: MessageRepository,
    val sessionManager: AuthRepository
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
            is DashboardAction.Logout -> {
                launchWithLoading {
                    sessionManager.logout()
                    AuthEventBus.emit(AuthEvent.LoggedOut)

                }
            }
            else -> {}
        }

    }

    init {
        fetchRemoteTasks() // Loading var
        observeDashboard() // sadece obserevce
    }

    private fun fetchRemoteTasks() {
        launchWithLoading {
            taskRepository.fetchTasks()
        }
    }
    fun observeDashboard() {
        viewModelScope.launch {
            taskRepository.getTasks()
                .combine(messageRepository.getDailyMessage()) { tasks, message ->
                    DashboardState(
                        totalTasks = tasks.size,
                        completedTasks = tasks.count { it.status == TaskStatus.DONE },
                        dailyMessage = message
                    )
                }
                .onEach { setState { it } }
                .launchIn(viewModelScope)
        }
    }

}
