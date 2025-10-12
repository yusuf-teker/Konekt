package org.yusufteker.konekt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.yusufteker.konekt.base.BaseState
import org.yusufteker.konekt.ui.base.PlatformBaseViewModel
import org.yusufteker.konekt.ui.base.UiEvent

@Composable
fun <S : BaseState, VM : PlatformBaseViewModel<S>> NavigationHandler(
    viewModel: VM,
    onNavigate: (NavigationModel) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateTo -> onNavigate(event.toModel())
                is UiEvent.NavigateWithData<*> -> onNavigate(event.toModel())
                is UiEvent.NavigateBack -> onNavigate(
                    NavigationModel(
                        route = Routes.Back,
                        isBack = true
                    )
                )
                else -> Unit
            }
        }
    }
}

data class NavigationModel(
    val route: Routes,
    val data: Any? = null,
    val popUpToRoute: Routes? = null,
    val inclusive: Boolean = false,
    val isBack: Boolean = false // Back stack pop için
)