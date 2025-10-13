package org.yusufteker.konekt.ui.screen.settings.presentation

import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.yusufteker.konekt.domain.repository.SettingsRepository
import org.yusufteker.konekt.feature.settings.SettingsAction
import org.yusufteker.konekt.feature.settings.SettingsState
import org.yusufteker.konekt.ui.base.PlatformBaseViewModel

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) :
    PlatformBaseViewModel<SettingsState>(SettingsState()) {

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.Init -> {}
            is SettingsAction.NavigateBack -> navigateBack()
            is SettingsAction.ChangeTheme -> {
                viewModelScope.launch {
                    settingsRepository.setThemeMode(action.mode)
                }
            }

        }
    }
    fun loadPreferences(){
        launchWithLoading {
            settingsRepository.observeThemeMode().onEach { cards ->
                setState { copy(themeMode = cards) }
            }.launchIn(viewModelScope)
        }
    }

   init {
       loadPreferences()
   }
}
