package org.yusufteker.konekt.feature.register

sealed interface RegisterAction {
    data object Init : RegisterAction
    data object NavigateBack : RegisterAction
    data class OnUsernameChange(val username: String) : RegisterAction
    data class OnEmailChange(val email: String) : RegisterAction
    data class OnPasswordChange(val password: String) : RegisterAction
    data class OnConfirmPasswordChange(val confirmPassword: String) : RegisterAction
    data object OnTogglePasswordVisibility : RegisterAction
    data object OnToggleConfirmPasswordVisibility : RegisterAction
    data object OnRegisterClick : RegisterAction
    data object OnLoginClick : RegisterAction
}