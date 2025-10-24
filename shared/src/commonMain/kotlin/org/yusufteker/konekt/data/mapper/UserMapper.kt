package org.yusufteker.konekt.data.mapper

import org.yusufteker.konekt.domain.models.User
import org.yusufteker.konekt.data.network.dto.UserDTO

fun UserDTO.toDomain(): User = User(
    id = id,
    username = username,
    email = email,
    avatarUrl = avatarUrl,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isActive = isActive
)
