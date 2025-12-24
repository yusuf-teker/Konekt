package org.yusufteker.konekt.ui.screen.login.presentation

import org.yusufteker.konekt.domain.repository.AuthRepository
import org.yusufteker.konekt.feature.login.LoginAction
import org.yusufteker.konekt.feature.login.LoginState
import org.yusufteker.konekt.ui.base.PlatformBaseViewModel
import org.yusufteker.konekt.ui.base.UiEvent
import org.yusufteker.konekt.ui.navigation.Routes
import org.yusufteker.konekt.util.Result

class LoginViewModel(
    private val authRepository: AuthRepository
) : PlatformBaseViewModel<LoginState>(LoginState()) {

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.Init -> init()
            is LoginAction.NavigateBack -> navigateBack()
            is LoginAction.OnEmailChange -> onEmailChange(action.email)
            is LoginAction.OnPasswordChange -> onPasswordChange(action.password)
            is LoginAction.OnTogglePasswordVisibility -> togglePasswordVisibility()
            is LoginAction.OnLoginClick -> login()
            is LoginAction.OnRegisterClick -> navigateToRegister()
        }
    }

    private fun init() { }

    private fun onEmailChange(email: String) {
        setState { copy(email = email, emailError = validateEmail(email)) }
        validateForm()
    }

    private fun onPasswordChange(password: String) {
        setState { copy(password = password, passwordError = validatePassword(password)) }
        validateForm()
    }

    private fun togglePasswordVisibility() {
        setState { copy(isPasswordVisible = !isPasswordVisible) }
    }

    private fun validateForm() {
        val s = state.value
        val canLogin = s.email.isNotBlank() &&
                s.password.isNotBlank() &&
                s.emailError == null &&
                s.passwordError == null
        setState { copy(canLogin = canLogin) }
    }

    private fun login() {
        val s = state.value
        if (!s.canLogin) return

        launchWithLoading {
            setState { copy(isLoading = true, errorMessage = null) }
            when (val result = authRepository.login(s.email, s.password)) {
                is Result.Success -> {
                    setState { copy(isLoading = false) }
                    sendUiEventSafe(UiEvent.NavigateTo(Routes.Dashboard))
                }
                is Result.Error -> {
                    setState { copy(isLoading = false, errorMessage = result.error.message ) }
                }
            }
        }
    }

    private fun navigateToRegister() {
        sendUiEventSafe(UiEvent.NavigateTo(Routes.Register))
    }
}
private fun validateEmail(email: String): String? {
    return when {
        email.isEmpty() -> null // Henüz kullanıcı yazmamış
        !email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")) ->
            "Invalid email format"
        else -> null
    }
}
private fun validatePassword(password: String): String? {
    return when {
        password.isEmpty() -> null
        password.length < 6 -> "Password must be at least 6 characters"
        else -> null
    }
}
