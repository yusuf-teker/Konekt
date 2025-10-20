package org.yusufteker.konekt.ui.screen.register.presentation

import kotlinx.coroutines.launch
import org.yusufteker.konekt.domain.repository.AuthRepository
import org.yusufteker.konekt.feature.register.RegisterAction
import org.yusufteker.konekt.feature.register.RegisterState
import org.yusufteker.konekt.ui.base.PlatformBaseViewModel
import org.yusufteker.konekt.ui.base.UiEvent
import org.yusufteker.konekt.ui.navigation.NavigationModel
import org.yusufteker.konekt.ui.navigation.Routes
import org.yusufteker.konekt.util.Result

class RegisterViewModel(
    private val authRepository: AuthRepository
) : PlatformBaseViewModel<RegisterState>(RegisterState()) {

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.Init -> init()
            is RegisterAction.NavigateBack -> navigateBack()
            is RegisterAction.OnUsernameChange -> onUsernameChange(action.username)
            is RegisterAction.OnEmailChange -> onEmailChange(action.email)
            is RegisterAction.OnPasswordChange -> onPasswordChange(action.password)
            is RegisterAction.OnConfirmPasswordChange -> onConfirmPasswordChange(action.confirmPassword)
            is RegisterAction.OnTogglePasswordVisibility -> togglePasswordVisibility()
            is RegisterAction.OnToggleConfirmPasswordVisibility -> toggleConfirmPasswordVisibility()
            is RegisterAction.OnRegisterClick -> register()
            is RegisterAction.OnLoginClick -> navigateToLogin()
        }
    }

    private fun init() {
        // Initial setup if needed
    }

    private fun onUsernameChange(username: String) {
        setState {
            copy(
                username = username,
                usernameError = validateUsername(username)
            )
        }
        validateForm()
    }

    private fun onEmailChange(email: String) {
        setState {
            copy(
                email = email,
                emailError = validateEmail(email)
            )
        }
        validateForm()
    }

    private fun onPasswordChange(password: String) {
        setState {
            copy(
                password = password,
                passwordError = validatePassword(password),
                confirmPasswordError = if (state.value.confirmPassword.isNotEmpty()) {
                    validateConfirmPassword(password, state.value.confirmPassword)
                } else null
            )
        }
        validateForm()
    }

    private fun onConfirmPasswordChange(confirmPassword: String) {
        setState {
            copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = validateConfirmPassword(state.value.password, confirmPassword)
            )
        }
        validateForm()
    }

    private fun togglePasswordVisibility() {
        setState { copy(isPasswordVisible = !isPasswordVisible) }
    }

    private fun toggleConfirmPasswordVisibility() {
        setState { copy(isConfirmPasswordVisible = !isConfirmPasswordVisible) }
    }

    private fun register() {
        val currentState = state.value

        if (!currentState.canRegister) return

        launchWithLoading {
            setState { copy(isLoading = true, errorMessage = null) }

            when (val result = authRepository.register(
                username = currentState.username,
                email = currentState.email,
                password = currentState.password
            )) {
                is Result.Success -> {
                    setState { copy(isLoading = false) }
                    // Navigate to home or login
                    sendUiEventSafe(UiEvent.NavigateTo(
                        route = Routes.Dashboard,
                        popUpToRoute = Routes.Register,
                        inclusive = true
                    ))
                }
                is Result.Error -> {
                    setState {
                        copy(
                            isLoading = false,
                            errorMessage = result.error.toString()
                        )
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        sendUiEventSafe(UiEvent.NavigateTo(Routes.Login))
    }

    private fun validateForm() {
        val currentState = state.value
        val canRegister = currentState.username.isNotBlank() &&
                currentState.email.isNotBlank() &&
                currentState.password.isNotBlank() &&
                currentState.confirmPassword.isNotBlank() &&
                currentState.usernameError == null &&
                currentState.emailError == null &&
                currentState.passwordError == null &&
                currentState.confirmPasswordError == null

        setState { copy(canRegister = canRegister) }
    }

    private fun validateUsername(username: String): String? {
        return when {
            username.isEmpty() -> null
            username.length < 3 -> "Username must be at least 3 characters"
            username.length > 20 -> "Username must be less than 20 characters"
            !username.matches(Regex("^[a-zA-Z0-9_]+$")) -> "Username can only contain letters, numbers and underscore"
            else -> null
        }
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isEmpty() -> null
            !email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")) -> "Invalid email format"
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

    private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return when {
            confirmPassword.isEmpty() -> null
            password != confirmPassword -> "Passwords do not match"
            else -> null
        }
    }
}