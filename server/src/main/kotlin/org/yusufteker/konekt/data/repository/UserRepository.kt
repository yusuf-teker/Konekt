package org.yusufteker.konekt.data.repository

import at.favre.lib.crypto.bcrypt.BCrypt
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.yusufteker.konekt.data.entity.UserEntity
import org.yusufteker.konekt.data.table.UserTable
import java.util.UUID

class UserRepository {

    fun register(username: String, email: String, password: String): UserEntity? {
        // 1️⃣ Şifreyi hashle
        val hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray())

        // 2️⃣ Veritabanına kaydet
        return transaction {
            val id = UUID.randomUUID().toString()
            val now = System.currentTimeMillis()

            val inserted = UserTable.insert {
                it[UserTable.id] = id
                it[UserTable.username] = username
                it[UserTable.email] = email
                it[UserTable.password] = hashedPassword
                it[UserTable.avatarUrl] = null
                it[UserTable.createdAt] = now
                it[UserTable.updatedAt] = now
                it[UserTable.isActive] = true
            }.resultedValues?.singleOrNull()

            inserted?.let {
                UserEntity(
                    id = it[UserTable.id],
                    username = it[UserTable.username],
                    email = it[UserTable.email],
                    password = it[UserTable.password],
                    avatarUrl = it[UserTable.avatarUrl],
                    createdAt = it[UserTable.createdAt],
                    updatedAt = it[UserTable.updatedAt],
                    isActive = it[UserTable.isActive]
                )
            }
        }
    }

    fun login(email: String, password: String): UserEntity? {
        return transaction {
            // 1️⃣ Kullanıcıyı email’e göre bul
            val userRow = UserTable.select { UserTable.email eq email }.singleOrNull() ?: return@transaction null

            // 2️⃣ Hash’leri karşılaştır
            val verified = BCrypt.verifyer()
                .verify(password.toCharArray(), userRow[UserTable.password])
                .verified

            if (verified) {
                // 3️⃣ Giriş başarılı
                UserEntity(
                    id = userRow[UserTable.id],
                    username = userRow[UserTable.username],
                    email = userRow[UserTable.email],
                    password = userRow[UserTable.password],
                    avatarUrl = userRow[UserTable.avatarUrl],
                    createdAt = userRow[UserTable.createdAt],
                    updatedAt = userRow[UserTable.updatedAt],
                    isActive = userRow[UserTable.isActive]
                )
            } else {
                null // giriş başarısız
            }
        }
    }

    fun findById(id: String): UserEntity? = transaction {
        UserTable.select { UserTable.id eq id }
            .singleOrNull()?.let {
                UserEntity(
                    id = it[UserTable.id],
                    username = it[UserTable.username],
                    email = it[UserTable.email],
                    password = it[UserTable.password],
                    avatarUrl = it[UserTable.avatarUrl],
                    createdAt = it[UserTable.createdAt],
                    updatedAt = it[UserTable.updatedAt],
                    isActive = it[UserTable.isActive]
                )
            }
    }

    fun getByEmail(email: String): UserEntity? = transaction {
        UserTable.select { UserTable.email eq email }.singleOrNull()?.let {
            UserEntity(
                id = it[UserTable.id],
                username = it[UserTable.username],
                email = it[UserTable.email],
                password = it[UserTable.password],
                avatarUrl = it[UserTable.avatarUrl],
                createdAt = it[UserTable.createdAt],
                updatedAt = it[UserTable.updatedAt],
                isActive = it[UserTable.isActive]
            )
        }
    }
}
