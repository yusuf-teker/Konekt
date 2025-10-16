package org.yusufteker.konekt.domain

import kotlinx.serialization.Serializable

// sunucu ile istemci (API) arasında taşınan veri modeli.
// DTO dış dünyaya açılan modeldir (örneğin API response)
// DTO’da sadece frontende gösterilecek alanlar olur

/* Client + Serverde api resposne için User modeli kullanılcak

@Serializable
data class UserDTO(
    val id: String,
    val username: String,
    val email: String,
    val avatarUrl: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val isActive: Boolean
)*/