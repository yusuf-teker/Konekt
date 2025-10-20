package org.yusufteker.konekt.feature.login

import org.yusufteker.konekt.base.BaseState

data class LoginState(
    override val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isPasswordVisible: Boolean = false,
    val canLogin: Boolean = false,
    val errorMessage: String? = null
) : BaseState {
    override fun copyWithLoading(isLoading: Boolean) = copy(isLoading = isLoading)
}
