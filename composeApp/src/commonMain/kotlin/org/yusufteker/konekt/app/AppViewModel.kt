package org.yusufteker.konekt.app


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.yusufteker.konekt.data.preferences.AppPreference
import org.yusufteker.konekt.domain.repository.SettingsRepository

class AppViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    var state = androidx.compose.runtime.mutableStateOf(AppState())
        private set

    init {
        observeTheme()
        // observer auth -> auth repo yazılacak
        // observer language -> settings repodan çekilir,
        // network status -> network monitor
    }

    private fun observeTheme() {
        viewModelScope.launch {
            settingsRepository.observeThemeMode().collectLatest { mode ->
                state.value = state.value.copy(themeMode = mode)
            }
        }
    }

    fun setTheme(mode: AppPreference.ThemeMode) {
        viewModelScope.launch {
            settingsRepository.setThemeMode(mode)
        }
    }
}
