package org.yusufteker.konekt.domain.models

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val email: String,
    val avatarUrl: String? = null,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val updatedAt: Long = Clock.System.now().toEpochMilliseconds(),
    val isActive: Boolean = true
)