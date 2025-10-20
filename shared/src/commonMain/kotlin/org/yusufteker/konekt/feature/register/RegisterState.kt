package org.yusufteker.konekt.feature.register

import org.yusufteker.konekt.base.BaseState

data class RegisterState(
    override val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val canRegister: Boolean = false
) : BaseState {
    override fun copyWithLoading(isLoading: Boolean) = copy(isLoading = isLoading)
}