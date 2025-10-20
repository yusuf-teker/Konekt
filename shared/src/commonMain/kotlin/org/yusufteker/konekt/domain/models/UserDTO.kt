package org.yusufteker.konekt.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: String,
    val username: String,
    val email: String,
    val avatarUrl: String? = null,
    val createdAt: Long,
    val updatedAt: Long,
    val isActive: Boolean
)