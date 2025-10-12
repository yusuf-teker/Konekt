package org.yusufteker.konekt.ui.base

import org.yusufteker.konekt.ui.navigation.NavigationModel
import org.yusufteker.konekt.ui.navigation.Routes

sealed class UiEvent {
    object NavigateBack : UiEvent()
    data class NavigateTo(
        val route: Routes,
        val popUpToRoute: Routes? = null,
        val inclusive: Boolean = false,
        val isBack: Boolean = false
    ) : UiEvent() {
        fun toModel() = NavigationModel(
            route = route,
            popUpToRoute = popUpToRoute,
            inclusive = inclusive,
            isBack = isBack
        )
    }

    data class NavigateWithData<T>(
        val route: Routes,
        val data: T,
        val popUpToRoute: Routes? = null,
        val inclusive: Boolean = false
    ) : UiEvent() {
        fun toModel() = NavigationModel(
            route = route,
            data = data,
            popUpToRoute = popUpToRoute,
            inclusive = inclusive,
        )
    }

    data class ShowLoading(val isLoading: Boolean) : UiEvent()
}
