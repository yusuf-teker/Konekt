package org.yusufteker.konekt.feature.settings

import org.yusufteker.konekt.data.preferences.AppPreference.ThemeMode


sealed interface SettingsAction {
    object Init : SettingsAction
    object NavigateBack : SettingsAction
     data class ChangeTheme(val mode: ThemeMode) : SettingsAction
}
