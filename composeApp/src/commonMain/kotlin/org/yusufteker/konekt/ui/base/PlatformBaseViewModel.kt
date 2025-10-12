package org.yusufteker.konekt.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.koin.core.component.KoinComponent
import org.yusufteker.konekt.base.BaseViewModel
import org.yusufteker.konekt.base.BaseState
import org.yusufteker.konekt.base.BaseViewModelImpl

open class PlatformBaseViewModel<S : BaseState>(
    initialState: S
) : ViewModel(), BaseViewModel<S> by BaseViewModelImpl(initialState), KoinComponent {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun sendUiEvent(event: UiEvent) {
        launchWithLoading {
            _uiEvent.emit(event)
        }
    }

    fun navigateBack() {
        sendUiEvent(UiEvent.NavigateBack)
    }

    fun navigateTo(route: org.yusufteker.konekt.ui.navigation.Routes, popUpToRoute: org.yusufteker.konekt.ui.navigation.Routes? = null, inclusive: Boolean = false) {
        sendUiEvent(UiEvent.NavigateTo(route, popUpToRoute, inclusive))
    }

    fun <T> navigateWithData(route: org.yusufteker.konekt.ui.navigation.Routes, data: T, popUpToRoute: org.yusufteker.konekt.ui.navigation.Routes? = null, inclusive: Boolean = false) {
        sendUiEvent(UiEvent.NavigateWithData(route, data, popUpToRoute, inclusive))
    }
}
