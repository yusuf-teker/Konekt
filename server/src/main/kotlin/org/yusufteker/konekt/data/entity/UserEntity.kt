package org.yusufteker.konekt.data.entity

import kotlinx.serialization.Serializable

//Exposed ile sorgu yaptığında satırları UserEntity nesnelerine dönüştürürsün.
// veritabanı işlemleri için lazım

// VERİTABANI İLE 1 E 1
@Serializable
data class UserEntity(
    val id: String,
    val username: String,
    val email: String,
    val password: String,
    val avatarUrl: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val isActive: Boolean
)
