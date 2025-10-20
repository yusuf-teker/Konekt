package org.yusufteker.konekt.data.mapper


import org.yusufteker.konekt.data.entity.UserEntity
import org.yusufteker.konekt.domain.models.User
import org.yusufteker.konekt.domain.models.UserDTO

fun UserEntity.toDomain(): User = User(
    id = id,
    username = username,
    email = email,
    avatarUrl = avatarUrl,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isActive = isActive
)

fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    username = username,
    email = email,
    avatarUrl = avatarUrl,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isActive = isActive,
    password = ""
)


fun UserEntity.toDTO(): UserDTO = UserDTO(
    id = id,
    username = username,
    email = email,
    avatarUrl = avatarUrl,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isActive = isActive
)
fun UserDTO.toDomain(): User = User(
    id = id,
    username = username,
    email = email,
    avatarUrl = avatarUrl,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isActive = isActive
)
