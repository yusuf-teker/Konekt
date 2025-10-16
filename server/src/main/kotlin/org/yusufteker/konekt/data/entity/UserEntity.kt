package org.yusufteker.konekt.data.entity

//Exposed ile sorgu yaptığında satırları UserEntity nesnelerine dönüştürürsün.
// veritabanı işlemleri için lazım
data class UserEntity(
    val id: String,
    val username: String,
    val email: String,
    val avatarUrl: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val isActive: Boolean
)