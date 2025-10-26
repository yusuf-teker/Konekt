package org.yusufteker.konekt.ui.popup

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.yusufteker.konekt.ui.base.UiText

class PopupManager {
    private val _popups = mutableStateListOf<PopupType>()
    val popups: List<PopupType> get() = _popups
    val snackbarHostState = SnackbarHostState()

    fun dismissAll() {
        val currentPopups = _popups.toList()
        _popups.clear()
        currentPopups.forEach { popup ->
            try {
                popup.onDismiss()
            } catch (e: Exception) {
                // Handle any dismiss errors
            }
        }
    }

    fun showPopup(popup: PopupType) {
        _popups.add(popup)
    }

    fun dismissPopup(popup: PopupType) {
        popup.onDismiss() // ekstra birşeyler yaplmak istenirse
        _popups.remove(popup)

    }

    fun showInfo(title: StringResource, message: StringResource, onDismiss: () -> Unit = {}) {
        showPopup(PopupType.Info(title, message, onDismiss))
    }

    fun showConfirm(
        title: UiText,
        message: UiText,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit = {},
        confirmLabel: UiText? = null,
        dismissLabel: UiText? = null

    ) {
        showPopup(PopupType.Confirm(title, message, onConfirm, onDismiss))
    }

    fun showError(message: StringResource, onDismiss: () -> Unit = {}) {
        showPopup(PopupType.Error(message, onDismiss))
    }

    fun showCustom(
        content: @Composable (onDismiss: () -> Unit) -> Unit, onDismiss: () -> Unit = {}
    ) {
        showPopup(PopupType.Custom(content, onDismiss))
    }


    // ============ Snackbar Methods ============

    /**
     * Basit snackbar göster
     */
    fun showSnackbar(
        scope: CoroutineScope,
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = duration
            )
        }
    }

    /**
     * Action butonlu snackbar göster
     */
    fun showSnackbarWithAction(
        scope: CoroutineScope,
        message: String,
        actionLabel: String,
        duration: SnackbarDuration = SnackbarDuration.Long,
        onActionPerformed: () -> Unit = {}
    ) {
        scope.launch {
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel,
                duration = duration
            )

            when (result) {
                androidx.compose.material3.SnackbarResult.ActionPerformed -> {
                    onActionPerformed()
                }
                androidx.compose.material3.SnackbarResult.Dismissed -> {
                    // Dismissed
                }
            }
        }
    }

    /**
     * UiText ile snackbar göster
     */
    fun showSnackbar(
        scope: CoroutineScope,
        message: UiText,
        duration: SnackbarDuration = SnackbarDuration.Short,
        getString: (UiText) -> String // Helper function to convert UiText to String
    ) {
        showSnackbar(
            scope = scope,
            message = getString(message),
            duration = duration
        )
    }

    /**
     * Başarı snackbar (yeşil arka plan için custom)
     */
    fun showSuccessSnackbar(
        scope: CoroutineScope,
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        showSnackbar(
            scope = scope,
            message = "✅ $message",
            duration = duration
        )
    }

    /**
     * Hata snackbar (kırmızı arka plan için custom)
     */
    fun showErrorSnackbar(
        scope: CoroutineScope,
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Long
    ) {
        showSnackbar(
            scope = scope,
            message = "❌ $message",
            duration = duration
        )
    }

    /**
     * Uyarı snackbar
     */
    fun showWarningSnackbar(
        scope: CoroutineScope,
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Long
    ) {
        showSnackbar(
            scope = scope,
            message = "⚠️ $message",
            duration = duration
        )
    }

    /**
     * Bilgi snackbar
     */
    fun showInfoSnackbar(
        scope: CoroutineScope,
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        showSnackbar(
            scope = scope,
            message = "ℹ️ $message",
            duration = duration
        )
    }
}
