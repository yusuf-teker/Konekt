package org.yusufteker.konekt.ui.popup

import androidx.compose.runtime.Composable
import konekt.composeapp.generated.resources.Res
import konekt.composeapp.generated.resources.cancel
import konekt.composeapp.generated.resources.confirm
import org.jetbrains.compose.resources.StringResource
import org.yusufteker.konekt.ui.base.UiText

sealed class PopupType {
    abstract val onDismiss: () -> Unit

    data class Info(
        val title: StringResource,
        val message: StringResource,
        override val onDismiss: () -> Unit = {}
    ) : PopupType()

    data class Confirm(
        val title: UiText,
        val message: UiText,
        val onConfirm: () -> Unit,
        override val onDismiss: () -> Unit = {},
        val confirmLabel: UiText = UiText.StringResourceId(Res.string.confirm),
        val dismissLabel: UiText =UiText.StringResourceId(Res.string.cancel)
    ) : PopupType()

    data class Error(
        val message: StringResource, override val onDismiss: () -> Unit = {}
    ) : PopupType()

    class Custom(
        val content: @Composable (onDismiss: () -> Unit) -> Unit,
        override val onDismiss: () -> Unit = {}
    ) : PopupType()

}
