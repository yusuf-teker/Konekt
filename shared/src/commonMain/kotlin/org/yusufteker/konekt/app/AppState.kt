package org.yusufteker.konekt.app

import org.yusufteker.konekt.base.BaseState
import org.yusufteker.konekt.data.preferences.AppPreference

data class AppState(
    val themeMode: AppPreference.ThemeMode = AppPreference.ThemeMode.SYSTEM,
    override val isLoading: Boolean = false,
    val errorMessage: String? = null
) : BaseState {
    override fun copyWithLoading(isLoading: Boolean) = copy(isLoading = isLoading)
}