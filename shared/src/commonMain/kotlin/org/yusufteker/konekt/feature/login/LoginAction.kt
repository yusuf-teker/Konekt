package org.yusufteker.konekt.feature.login

sealed interface LoginAction {
    object Init : LoginAction
    object NavigateBack : LoginAction

    data class OnEmailChange(val email: String) : LoginAction
    data class OnPasswordChange(val password: String) : LoginAction
    object OnTogglePasswordVisibility : LoginAction
    object OnLoginClick : LoginAction
    object OnRegisterClick : LoginAction
}
