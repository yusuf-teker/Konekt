package org.yusufteker.konekt.data.network.dto

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