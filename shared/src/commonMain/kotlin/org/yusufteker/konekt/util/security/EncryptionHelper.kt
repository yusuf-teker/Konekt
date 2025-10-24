package org.yusufteker.konekt.util.security



expect class EncryptionHelperImpl() {
    fun encrypt(value: String): String
    fun decrypt(value: String): String
}