package org.yusufteker.konekt.util.security

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

actual class EncryptionHelperImpl actual constructor() {

    private val secretKey = "KonektSecretKey1" // 16-byte
    private val keySpec = SecretKeySpec(secretKey.toByteArray(), "AES")

    actual fun encrypt(value: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        val encrypted = cipher.doFinal(value.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    actual fun decrypt(value: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        val decoded = Base64.decode(value, Base64.DEFAULT)
        return String(cipher.doFinal(decoded))
    }
}