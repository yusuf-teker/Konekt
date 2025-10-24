package org.yusufteker.konekt.domain.models.response

import kotlinx.serialization.Serializable
import org.yusufteker.konekt.data.network.dto.UserDTO

@Serializable
data class AuthResponse(
    val token: String,
    val user: UserDTO
)