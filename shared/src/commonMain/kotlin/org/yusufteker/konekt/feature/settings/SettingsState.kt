package org.yusufteker.konekt.feature.settings

import org.yusufteker.konekt.base.BaseState
import org.yusufteker.konekt.data.preferences.AppPreference

data class SettingsState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val themeMode: AppPreference.ThemeMode = AppPreference.ThemeMode.SYSTEM

) : BaseState {
    override fun copyWithLoading(isLoading: Boolean) = copy(isLoading = isLoading)
}
